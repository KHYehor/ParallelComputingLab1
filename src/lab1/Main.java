package lab1;

public class Main {
    public synchronized static void main(String[] args) throws InterruptedException {
        /**
         * Variant20
         * O = SORT(P) * SORT(MR * MS);
         */
        Vector P = new Vector("./task1/P.txt").initWithRandomValues();
        Matrix MR = new Matrix("./task1/MR.txt").initWithRandomValues();
        Matrix MS = new Matrix("./task1/MS.txt").initWithRandomValues();
        Thread task1 = new Thread(() -> {
            // 1. SORT(P)
            P.sort();
        });
        // Copy values, because they are variables
        Matrix finalMR = MR;
        Matrix finalMS = MS;
        Thread task2 = new Thread(() -> {
            Matrix Result = finalMR
                    // 2. MR*MS;
                    .multiplyWithMatrix(finalMS)
                    // 3. SORT(MR*MS);
                    .sort();
            // 4. SORT(P) * SORT(MR*MS)
            P.multiplyWithMatrix(Result)
                    .printResult()
                    .saveFinalResult("./task1/O.txt");
        });
        task1.start();
        task2.start();
        // Block main thread to wait other thread finishing their tasks
        task1.join();
        task2.join();
        /**
         * Variant20
         * MG = MB * MS + MC * (MR + MM);
         */
        Matrix MB = new Matrix("./task2/MB.txt").initWithRandomValues();
        MS = new Matrix("./task2/MS.txt").initWithRandomValues();
        Matrix MC = new Matrix("./task2/MC.txt").initWithRandomValues();
        MR = new Matrix("./task2/MR.txt").initWithRandomValues();
        Matrix MM = new Matrix("./task2/MM.txt").initWithRandomValues();
        // Copy values, because they are variables
        Matrix finalMS1 = MS;
        Thread task3 = new Thread(() -> {
            // 1. MB * MS
            MB.multiplyWithMatrix(finalMS1);
        });
        Matrix finalMR1 = MR;
        Thread task4 = new Thread(() -> {
            finalMR1
                    // 2. MR + MM
                    .sumWithMatrix(MM)
                    // 3. MC * (MR + MM)
                    .multiplyWithMatrix(MC)
                    // 4. (MB * MS) + (MC * (MR + MM))
                    .sumWithMatrix(MB)
                    .printResult()
                    .saveFinalResult("./task2/MG.txt");
        });
        task3.start();
        task4.start();
        // Block main thread to wait other thread finishing their tasks
        task3.join();
        task4.join();
    }
}
