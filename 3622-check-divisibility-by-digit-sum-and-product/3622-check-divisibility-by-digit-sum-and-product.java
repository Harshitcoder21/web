class Solution {
    public boolean checkDivisibility(int n) {
       // Scanner sc=new Scanner(System.in);
        // n=sc.nextInt();
         
         //Sum of digit 
         int original=n;
         int sum=0;
         int prod=1;
         while(n>0){
            sum+=n%10;
            prod=prod*(n%10);
            n=n/10;
         }
         int sum2=sum+prod;
         if(original%sum2==0){
            return true;
         }
         else {
            return false;
         }
        
    }
}