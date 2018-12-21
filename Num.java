import java.util.*;
public class Num implements Comparable<Num>
{
    static long defaultBase = 10;  // Change as needed
    long base = 10;  // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    boolean isNegative;  // boolean flag to represent negative numbers
    int len;  // actual number of elements of array that are used;  number is stored in arr[0..len-1]

    public Num(String s) 
    {        
        //System.out.println("The recommended size of the array for String "+s+" is going to be... ");
        //System.out.println((((s.length()+1)/Math.log10(base))+1));
        //long each_item=(long)(((s.length()+1)/Math.log10(base))+1);
        //System.out.println(each_item);
        this.arr=new long[s.length()];
        int pos = 0;
        //System.out.println(s.charAt(0));
        if(s.charAt(0)=='-')
        {
            this.isNegative=true;
            for(int i = s.length()-1; i>=1;i--)
            {
                this.arr[pos]=Character.getNumericValue(s.charAt(i));
                pos++;
            }
            this.len=pos;
        }
        else
        {
            this.isNegative = false;
            //System.out.println("String length is "+s.length());
            for(int i = s.length()-1; i>=0;i--)
            {
                //System.out.println("pos="+this.arr.length);
                //System.out.println("character is "+Character.getNumericValue(s.charAt(i)));
                this.arr[pos]=Character.getNumericValue(s.charAt(i));
                pos++;
            }
            this.len=pos;
        }
        if(this.base != 10){
        Num converted_num=new Num();
        converted_num.arr=new long[(int)(((s.length()+1)/Math.log10(base))+1)];
        converted_num=this.convertBase((int)this.base);
        this.arr=converted_num.arr;
        this.len=converted_num.len;
        }
        //this.convertBase((int)this.base);
        //this.arr = x.arr;
    }
    
    public Num(long x) 
    {
        long[] x_array=new long[(int) (((Long.toString(x).length()+1)/Math.log10(base))+1)];
        //System.out.println("Long is "+Long.toString(x).length());
        long temp=x;
        //System.out.println("Converted is "+temp);
        long mod;
        long b = this.base;
        int k=0;
        while(temp>0)
        {
            mod=temp%b;
            x_array[k]=mod;
            //System.out.print(x_array[k]);
            k++;
            temp=temp/b;
        }
        this.len=k;
        this.arr=x_array;
        print_result(x_array);
    }
    public Num()
    {
        
    }
    
    public static Num add(Num a, Num b) 
    {    
        long[] a1=a.arr;
        long[] b1=b.arr;
        long a_base = a.base;
        int a_len = a.len;
        int b_len = b.len;
       // System.out.println("-----------------------------");
       // System.out.println(a_len+"a[0] is "+a.arr[0]+"a[1] is "+a.arr[1]+"a[2] is "+a.arr[2]);
        //System.out.println(b_len);
        long[] result;
        int order=0;
        int show=a.compareTo(b);
//        if(show>0)
//            order=0;
//        else
//            order=1;
        //System.out.println("ISnegative = "+a.isNegative+" A length is "+a_len);
        //System.out.println("ISnegative = "+b.isNegative+" B length is "+b_len);
        if(a.isNegative==true && b.isNegative==false)
        {
            if(show>0)
            {
                order=1;
                //Just an experimental approach
                a.isNegative=false;
                result=actual_subtraction(a1,b1,a_len,b_len,a_base);
                //result=sub_result.arr;
                a.isNegative=true;
            }
            else
            {
                order=0;
                //Just an experimental approach
                a.isNegative=false;
                result=actual_subtraction(b1,a1,b_len,a_len,a_base);
               // System.out.println("received "+Arrays.toString(result));
                a.isNegative=true;
            }  
        }
        else if(a.isNegative==false && b.isNegative==true)
        {
            if(show<0)
            {
                order=1;
                b.isNegative=false;
                result=actual_subtraction(b1,a1,b_len,a_len,a_base);
                b.isNegative=true;
            }
            else
            {
                order=0;
                b.isNegative=false;
                result=actual_subtraction(a1,b1,a_len,b_len,a_base);
                b.isNegative=true;
            }
        }
        else if(a.isNegative==true && b.isNegative==true)
        {
            result=actual_add(a1,b1,a_len,b_len,a_base);
            order=1;
        }
        else
        {
//            System.out.println("--------------");
//            System.out.println(Arrays.toString(a.arr));
//            System.out.println(Arrays.toString(b.arr));
//            System.out.println(a_len);
//            System.out.println(b_len);
            result=actual_add(a1,b1,a_len,b_len,a_base);
        }
        
        if(order==1)//&& result[result.length-1]!='-')
        {
            result[result.length-1]='-';
        }
        Num actual_result=new Num();
        actual_result.arr=result;
        actual_result.len = result.length;
//        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println("Addition summary:");
//        print_result(a.arr);
//        System.out.println("+"+a.len);
//        print_result(b.arr);
//        System.out.println("Result is : ");
//        print_result(actual_result.arr);
//        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        
        return actual_result;
    }
    public static long[] actual_add(long[] a1, long[] b1,int a_len, int b_len, long a_base)
    {
        int size=a_len>b_len?a_len:b_len;
        //System.out.println("SIze is "+size);
        long[] result=new long[size+2];
        long temp;
        long carry=0;
        int k=0;
        while(a_len!=0 && b_len!=0)
        {
            temp=a1[k]+b1[k]+carry;
//            System.out.println("Temp os "+temp);
            long temp2=temp;
            if(temp>=a_base)
            {
                temp=temp%a_base;
                result[k]=temp;
                carry=temp2/a_base;
                k++;
            }
            else
            {
                result[k]=temp;
                k++;
                carry=0;
            }
            a_len--;
            b_len--;
        }
        if(a_len==0)
        {
            while(b_len!=0)
            {
                result[k]=b1[k]+carry;
                carry=0;
                k++;
                b_len--;
            }
            if(carry!=0)
            {
                result[k]=carry;
            }
        }
        else
        {
            while(a_len!=0)
            {
                //System.out.println("Encountered "+a1[k]);
                result[k]=a1[k]+carry;
                if(result[k]>a_base)
                {
                    result[k]=result[k]%a_base;
                    carry=result[k]/a_base;
                }
                else
                    carry=0;
                k++;
                a_len--;
            }
            if(carry!=0)
            {
                result[k]=carry;
                k++;
            }
        }
        return result;
    }
    public static Num subtract(Num a, Num b) 
    {
        long[] result;
        long a1[]=a.arr;
        long b1[]=b.arr;
        //Object ran=a1;
        int a_len=a.len;
        int b_len=b.len;
        long a_base = a.base;
        int order=0;//=(int)objects[2];
//        System.out.println(Arrays.toString(a.arr)+"  lrn is "+a_len);
//        System.out.println(Arrays.toString(b.arr)+"  lrn is "+b_len);
        int show=a.compareTo(b);
//        if(show>0)
//            order=0;
//        else
//            order=1;
        
//        System.out.println("sign is "+a.isNegative);
//        System.out.println("sign is "+b.isNegative);
        if(a.isNegative==true && b.isNegative==false)
        {
            a.isNegative=false;
            result=actual_add(a1,b1,a_len,b_len,a_base);
            a.isNegative=true;
            order=1;
        }
        else if(a.isNegative==false && b.isNegative==true)
        {
            b.isNegative=false;
            result=actual_add(a1,b1,a_len,b_len,a_base);
            b.isNegative=true;
            order=0;
        }
        else if(a.isNegative==true && b.isNegative==true)
        {
            if(show>0)
            {
                order=1;
                result=actual_subtraction(a1,b1,a_len,b_len,a_base);
                
            }
            else
            {
                order=0;
                result=actual_subtraction(b1,a1,b_len,a_len,a_base);    
            }
        }
        else
        {
            if(show>=0)
            {
                order = 0;
                result=actual_subtraction(a1,b1,a_len,b_len,a_base);
            }
            else
            {
                order = 1;
                result=actual_subtraction(b1,a1,b_len,a_len,a_base);
            }
            
        }
        if(order==1)
        {
            result[result.length-1]='-';//This will be converted to 45 in ascii.
        }
        Num actual_result=new Num();
        actual_result.arr=result;
        actual_result.len = result.length;
//        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println("Subtraction summary:");
//        print_result(a.arr);
//        System.out.println("-");
//        print_result(b.arr);
//        System.out.println("Result is : ");
//        print_result(actual_result.arr);
//        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        return actual_result;
    }
    public static long[] actual_subtraction(long[] a1, long[] b1,int a_len, int b_len, long a_base)
    {
        long temp;
        int k=0;
        int carry=0;
        int size=a_len>b_len?a_len:b_len;
        long[] result=new long[size+2];
        while(a_len!=0 && b_len!=0)
        {
            temp=a1[k]-b1[k]+carry;
             if(temp<0)
            {
                a1[k]=a1[k]+a_base;
                temp=a1[k]-b1[k];
                result[k++]=temp+carry;
              //  System.out.println("result= "+result[k-1]);
                carry=-1;
            }
            else
            {
                result[k++]=temp;
                //System.out.println("result== "+result[k-1]);
                carry=0;
            }
            a_len--;
            b_len--;
        }        
        if(a_len==0)
        {
            while(b_len!=0)
            {
                result[k]=b1[k]+carry;
                k++;
                b_len--;
            }
        }
        else
        {
            while(a_len!=0)
            {
                temp=a1[k]+carry;
                if(temp<0)
                {
                    a1[k]=a1[k]+a_base;
                    result[k]=a1[k]+carry;
                    k++;
                    carry=-1;
                }
                else
                {
                    result[k]=a1[k]+carry;
                    k++;
                    carry=0;
                }
                a_len--;
            }
        }
        return result;
    }
    public static Object compare_arrays(long[] a, long[] b,int x, int y)
    {
        int flag=0;
          while(x!=0 && y!=0)
          {
              x--;
              y--;
             // System.out.println("COmapring a[x]"+a[x]+" wiyh b[y]"+b[y]);
              if(a[x]>b[y])
              {
                //  System.out.println("AA is greater than B");
                  return new Object[]{a,b,flag};
              }
              else if(b[y]>a[x])
              {
                  //System.out.println("BB is greater than A");
                  flag=1;
                  return new Object[]{b,a,flag};
              }
              else
              {
                  continue;
              }
          }
            
        return null;
    }
    
    public static Num product(Num a, Num b) {
        Num final_result;
        long[] a1=a.arr;
        long[] b1=b.arr;
        long a_base = a.base;
        int a_len = a.len;
        int b_len = b.len;
//        System.out.println(a_len);
//        System.out.println(b_len);
       // long[][] array = new long[b_len][];
        String[] array = new String[b_len];
        long[] result=new long[a_len+b_len];
        
        Num result1 = new Num("00");
        int j = 0;
        for(int i=0; i<b_len; i++) {
            int carry = 0;
            long temp = 0;
            long temp1=0;
            j = 0;
         //   array[i] = new long[a_len+i+1];
            String intermediateRes = "";
            intermediateRes = intermediateRes+ String.join("", Collections.nCopies(i, "0"));
           // intermediateRes = new String(new char[i]).replace("\0", '0');
            for(j=i; j<a_len+i;j++) {
                temp=b1[i]*a1[j-i];
                temp = temp + carry;
                temp1=temp;
              //  System.out.println("in product");
                if(temp>=a_base)
                {
                    temp=temp%a_base;                   
                    long res1 = temp;
                    intermediateRes += Long.valueOf(res1).toString();
                    temp=temp1/a_base;
                    carry=(int)temp;
                }
                else
                {
                    long res1 = temp;
                    
                    intermediateRes += Long.valueOf(res1).toString();
                    carry=0;
                }
            }
            if(carry !=0) {
                intermediateRes +=  carry;
            }
          //  System.out.println("res"+intermediateRes);
          // System.out.println("interres "+intermediateRes);
           String s2 = new StringBuffer(intermediateRes).reverse().toString();
           
           String s3 = (new Num(s2)).toString();
         //  System.out.println();
           if (!(s3.isEmpty())) {
             Num nextRes = new Num(s3);
             result1 = add(result1, nextRes);
             //System.out.println("result1 is set");
           }
        }
      //  System.out.println("array result"+ Arrays.toString(array));
       // System.out.println(Arrays.toString(array));
        //result=array[0];
//        result1 = new Num("00");
//        System.out.println("-------------");
//        for(int z=0;z<array.length;z++)
//        {
//            if (array[z].isEmpty() == false) {
//                
//            //    System.out.println("arr"+array[z]);
//            Num nextRes = new Num(array[z]);
//            result1 = add(result1, nextRes);
//            }
//        }
        if (!(a.isNegative == true && b.isNegative == true)) {
            if (a.isNegative == true || b.isNegative == true) {
                result[result.length-1] = '-';
            }
        }
       
        String s4 = result1.toString();
        //System.out.println("Check once");
        //print_result(result1.arr);
//        System.out.println("s3"+s4);
        if(s4.isEmpty()) {
        	final_result=new Num(0);  
        } else {
        final_result=new Num(s4);  
        }
        //final_result.arr=result1.arr;
       // final_result.len = a_len+b_len;//It was a_len+b_len-1
//        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//        System.out.println("Product summary:");
//        print_result(a.arr);
//        System.out.println("-");
//        print_result(b.arr);
//        System.out.println("Result is : size of "+final_result.arr.length);
//        print_result(final_result.arr);
//        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        
        //return final_result;
        return final_result;
    }

    
    // Use divide and conquer
    public static Num power(Num a, long n) {
        if(n<0)
        {
            return null;
        }
        else if(n==0)
        {
            return new Num(1);
        }
        else if(n==1)
        {
            String s12 = a.toString();
            return new Num(s12);
            //return a;
        }
        else
        {
            Num res;
            if(n%2 == 0)
            {
                res=product(a,a);
                String s13 = res.toString();
              //  System.out.println(s13);
                Num rec_sub_pow=new Num(s13);
              // System.out.println("in power------------------------");
                return power(rec_sub_pow,n/2);
            }
           else
            {
                res = product(a,a);
                String s13 = res.toString();
                Num rec_sub_pow=new Num(s13);
               // System.out.println("in power--------------------------");
                return product(a,power(rec_sub_pow,n/2));
               //return product(a,power(a,n-1));
            }
        }
    }
    
    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) 
    {
        long numerator=0;
        long denominator=0;
        long ba=a.base;
        long bb=b.base;
        int k=0;
        while(k<a.len)
        {
            numerator+=a.arr[k]*(Math.pow(ba, k));
            k++;
        }
        k=0;
        while(k<b.len)
        {
            denominator+=b.arr[k]*(Math.pow(bb, k));
            k++;
        }
        
        if(denominator==0)
        {
          //  System.out.println("Null pointer exception");
            throw new ArithmeticException();
            //return null;
        }
        else if(numerator==0)
        {
            return new Num(0);
        }
        Num final_result;//=new Num();
        float low=0;
        float high=numerator>denominator?numerator:denominator;
        float mid;
        mid=(low+high)/2;
        while(low<=high)
        {
            float temp = denominator;
            float result=temp*mid;
            if(result>numerator)
            {
                high=mid;
            }
            else if(result<numerator)
            {
                low=mid;
            }
            else
            {
                //System.out.println("Equal, and M is "+mid);
                //System.out.println("Low is "+low);
                //System.out.println("High is "+high);
                break;
            }
            mid=(low+high)/2;    
        }
        //String res=Float.toString(mid);
        long res1=(long) mid;       
        final_result=new Num(res1);
        //System.out.println("?------------>");
        final_result.len = Long.toString(res1).length();
        print_result(final_result.arr);
        return final_result;
    }
    
    // return a%b
    public static Num mod(Num a, Num b) 
    {
        Num mod = new Num();
        Num pro = new Num();
        pro = product(divide(a,b),b);
        String s1 = pro.toString();
        Num rec_pro=new Num(s1); // Creating this because pro.len is not set in the constructor.
    //    System.out.println("Product:"+s1);
        mod = subtract(a,rec_pro);
        String s = mod.toString();
    //    System.out.println("S:"+s);
        return(mod);
    }
    // Use binary search
    public static Num squareRoot(Num a)
    {
        long numerator=0;
        int k=0;
        while(k<a.arr.length)
        {
            numerator+=a.arr[k]*(Math.pow(a.base, k));
            k++;
        }
        if(a.isNegative == true)
            return null;
        if(numerator == 1)
            return new Num(1);
        if(numerator == 0)
            return new Num(0);
        long[] low = new long[a.arr.length - 2];
        String low1 = String.join("",
                Arrays.stream(low).mapToObj(String::valueOf).toArray(n -> new String[n]));
        Num low2 = new Num(low1);
        String high1 = a.toString();
        Num high2 = new Num(high1);
        Num mid;
        mid = add(low2,high2);
        mid = mid.by2();
        boolean isLowLesser = true;
        Num temp = new Num(high1);
        
        while(isLowLesser)
        {
            Num result=product(mid,mid);
            String result1 = result.toString();
            result = new Num(result1);
            if(result.compareTo(temp) > 0)
            {
                String mid1 = mid.toString();
                high2 = new Num(mid1);
            }
            else if(result.compareTo(temp) < 0 )
            {
                if(low2.compareTo(mid) == 0) 
                    break;
                String mid2 = mid.toString();
                low2 = new Num(mid2);
            }
            else
            {
                break;
            }
        
            high1 = high2.toString();
            high2 = new Num(high1);
            low1 = low2.toString();
            if(low1.isEmpty()) {
                low2 = new Num("0");
            } else {
            low2 = new Num(low1);
            }
               mid = add(low2,high2);
            mid = mid.by2();           
            String mid1 = mid.toString();
            mid = new Num(mid1);           
            int value = low2.compareTo(high2);
            if (value < 0) { 
                isLowLesser = true;
            } else {
                isLowLesser = false;
            }
        }
        Num final_result=mid;
        final_result.len = mid.arr.length;
        return final_result;
    }
    
    
    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) 
    {
          int x=this.len;
          int y=other.len;
          if(x>y)
          {
           //   System.out.println("A is greater than B");
              return 1;
          }
          else if(y>x)
          {
            //  System.out.println("B is greater than A");
              return -1;
          }
          else
          {
            while(x!=0 && y!=0)
            {
                x--;
                y--;
            //    System.out.println("COmapring a[x]"+this.arr[x]+" wiyh b[y]"+other.arr[y]);
                if(this.arr[x]>other.arr[y])
                {
               //     System.out.println("AA is greater than B");
                    return 1;
                }
                else if(other.arr[y]>this.arr[x])
                {
                 //   System.out.println("BB is greater than A");
                    return -1;
                }
                else
                {
                    continue;
                }
            }
            return 0;
          }
     }
    
    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {
//        int base = (int)this.base;
//        System.out.println("base"+base);
//        Num convertedArray = this.convertBase(base);
//        for(int i=0;i<convertedArray.len;i++) {
//            System.out.print(convertedArray.arr[i] +" ");
//        }
        
        for(int i=0;i<this.arr.length;i++) {
            System.out.print(this.arr[i] +" ");
        }
    }
    public static void  print_result(long[] x)
    {
        int flag=0;
        for(int i=x.length-1;i>=0;i--)
        {
            if(x[i]==0 && flag==0)
            {
                continue;
            }
            if(x[i]==45)
            {
               System.out.print("-");
                flag=1;
            }
            else if(x[i]==-1)
            {
                System.out.print("-1 ");
                flag=1;
            }
            else
            {
                System.out.printf("%d",x[i]);
                flag=1;
            }
            
        }
        System.out.println();
    }
    
    
    // Return number to a string in base 10
    public String toString() {
        long[] temp=this.arr;
        //System.out.println("Starts");
       //print_result(temp);
        //System.out.println("ends");
        
        //long temp_res;
        String[] answer=new String[this.arr.length]; 
        for(int i=temp.length-1;i>=0;i--)
        {
            answer[temp.length-(i+1)]=String.valueOf(temp[i]);
        }
        
        String answer1 = String.join("", answer);
        answer1 = answer1.replaceFirst("^0*", "");
       // System.out.println("answer"+answer1);
        return answer1;
    }
    
    public long base() 
    { 
        return this.base; 
    }
    
    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
        int baseLength = String.valueOf(newBase).length();
        long newBase1 = (long)newBase;
        long[] finalArray =  new long[(int)(((this.arr.length+1)/Math.log10(newBase))+1)];
        long Reminder = 0;
        int j = finalArray.length-1;
        boolean exitLoop = false;
        //to be changed when to string is implemented properly
        String originalNumber = String.join("",
                Arrays.stream(this.arr).mapToObj(String::valueOf).toArray(n -> new String[n])); 
        String originalNumber1 = new StringBuffer(originalNumber).reverse().toString();
        String number = originalNumber1;
        Num result = new Num();
        result.arr =  new long[(int)(((this.arr.length+1)/Math.log10(newBase))+1)];
        int k =0;
        Num temp, temp1;
        Num actual_temp=new Num(0);
        if(newBase==10)
        {
            int val=this.len;//It was this.arr.length;
            //int power=0;
            for(int c=0;c<val;c++)
            {
                String bases=Long.toString(this.base);
                //System.out.println("Stared c is "+(long)c);
                temp1=Num.power(new Num(bases), (long)c);
                //System.out.println("Temporary prod is ");
                print_result(temp1.arr);
                //System.out.println("End of prod result");
                
                String element= Long.toString(this.arr[c]);
                temp=Num.product(temp1,new Num(element));
                //Setting the base to 10 to override the class this.base.
                long initial_base=actual_temp.base;
                actual_temp.base=10;
                temp.base=10;
                actual_temp=add(actual_temp,temp);
                actual_temp.base=initial_base;
                temp.base=initial_base;
                //System.out.println("Temporary result is ");
                ///print_result(actual_temp.arr);
                //System.out.println("End of temporary result");
            }
            k=actual_temp.arr.length;
            result=actual_temp;
        }
        else
        {
            while (!(exitLoop)) {
                String quotient = number;
                number = "";
                Reminder = 0; 
                int lengthOfSubArray = quotient.length();
                for (int i=0;i<lengthOfSubArray;i++) {
                    String s = "";
                    char s1 = quotient.charAt(i);
                    s = String.valueOf(Reminder) + s1;
                    long value = Long.valueOf(s);
                    Reminder = value% newBase1;
                    long quotient1 = value/newBase1;
                    number = number + String.valueOf(quotient1);
                }
//                System.out.println("final_Reminder"+Reminder);
//                System.out.println("j="+j);
                finalArray[j] = Reminder;
                j--;
                result.arr[k] = Reminder;
                k++;
                if (number.matches("^[0]+$")){
                    exitLoop = true;
                }
            }
        }
        
        result.len = k;
        return result;
    }
    
    // Divide by 2, for using in binary search
    public Num by2() {
        Num final_answer=new Num(this.arr[0]);
        long[] temp = this.arr;
        long answer[]=new long[this.len];
        int k=this.len-1;
        for(int i=temp.length-1;i>=0;i--)
        {
            long res = temp[i]/2;
            answer[k]=res;
            k--;
            long carry=temp[i]%2;
            if(carry!=0)
            {
                if(i-1>=0)
                {
                    temp[i-1]+=10;
                }
            }
        }
        final_answer.arr=answer;
        final_answer.len = answer.length;
        return final_answer;
    }
    
    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) {
        Stack<Num> st = new Stack<Num>();
        Num result;
        for(int i=0;i<expr.length;i++) {
            if(expr[i] == "*" ||expr[i] == "-" ||expr[i] == "+" ||expr[i] == "/" || expr[i]=="^" ) {
                String op = expr[i];
                Num num1 = st.pop();
                Num num2 = st.pop();
                if(op == "+") {
                    result = add(num1,num2);
                    st.push(result);
                } else if (op == "-") {
                    result = subtract(num2,num1);
                    st.push(result);                
                } else if (op == "*") {
                    result = product(num2,num1);
                    st.push(result);
                } else if (op == "/") {
                    result = divide(num2,num1);
                    st.push(result);
                }
                else if (op == "%") {
                    result = mod(num1,num2);
                    st.push(result);
                }
                else if (op == "^") {
                    //String raiseTo = num2.toString();
                    Num raiseTo = num2.convertBase(10);
                    String raiseTo1 = raiseTo.toString();
                    result = power(num1,Long.valueOf(raiseTo1));
                    st.push(result);
                }
            } else {
                Num value = new Num(expr[i]);
                st.push(value);
            }
        }
        Num actual_result=new Num("10");
        actual_result.arr=st.pop().arr;
        return actual_result;
    }
    
  //This function gives an int which resembles the precedence of the operators.
    // The higher the number is, higher the priority.
    public static int give_precedence(String a)
    {
        switch(a)
        {
            case "^":
                return 4;
            case "*":
                return 3;
            case "/":
                return 3;
            case "%":
                return 3;
            case "+":
                return 2;
            case "-":
                return 2;
        }
        return 1;
    }
    // Evaluate an expression in infix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "(", ")", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    
    public static Num evaluateInfix(String[] expr) {
        Stack<String> stack=new Stack<>();
        //int flag=0;
     //   System.out.println("Expression length is "+expr.length);
        String[] postfix=new String[expr.length];
        int i;
        int k=0;
        int red_count=0;
        for(i=0;i<expr.length;i++)
        {
            //System.out.println();
            //System.out.println("Entering is "+expr[i]);
            if(expr[i]=="(")
            {
                stack.push(expr[i]);
                red_count++;
            }
            else if(expr[i]==")")
            {
                
                //stack.pop();
                while(stack.isEmpty()==false && stack.peek()!="(")
                {
                    postfix[k++]=stack.pop();
                }
                stack.pop();//Discarding matching parenthesis.
                red_count++;
            }
            else if(expr[i] == "*" ||expr[i] == "-" ||expr[i] == "+" ||expr[i] == "/" || expr[i]=="%" || expr[i]=="^")
            {
                int prec1=give_precedence(expr[i]);
                //System.out.println("prec 1 is "+prec1);
                int prec2=stack.isEmpty()==true?1:give_precedence(stack.peek());
                //System.out.println("Prec 2 is "+prec2);
                if(prec1>prec2)
                {
                    stack.push(expr[i]);
                    //System.out.println("Inserted in stack is "+expr[i]);
                }
                if(prec1==prec2 && prec1!=4)
                {
                    postfix[k++]=stack.pop();
                    prec2=stack.isEmpty()==true?1:give_precedence(stack.peek());
                    if(prec1<prec2)
                    {
                        while(prec1<prec2)
                        {
                            postfix[k++]=stack.pop();
                            stack.push(expr[i]);
                            break;
                            
                        }
                        //System.out.println("Inserted in stack is "+expr[i]);
                    }
                    else
                    {
                        stack.push(expr[i]);
                        //System.out.println("Inserted in stack is "+expr[i]);
                    }
                    //prec2=give_precedence(stack.peek());
                }
                else if(prec1==prec2 && prec1==4)
                {
                    //^ operator is evaluated from left to right. So, we push it into the stack.
                    stack.push(expr[i]);
                    //System.out.println("Inserted in stack is "+expr[i]);
                }
                else if(prec1<prec2)
                {
                    while(prec1<prec2)
                    {
                        postfix[k++]=stack.pop();
                        prec2=stack.isEmpty()==true?1:give_precedence(stack.peek());
                    }
                    stack.push(expr[i]);
                    
                }
            }
            else
            {
                //This is a number (digit)
                //System.out.println("K is "+k+" and i is "+i);
                postfix[k]=expr[i];
                k++;
            }
            //System.out.println("Inserted is "+postfix);
        }
        if(i==expr.length && stack.isEmpty()==false)
        {
            while(stack.isEmpty()==false)
            {
                postfix[k++]=stack.pop();
            }
        }
        int real_size_of_String_array = postfix.length-red_count;
        
        String[] infix_to_postfix=new String[real_size_of_String_array];
        for(int j=0;j<real_size_of_String_array;j++)
        {
            infix_to_postfix[j]=postfix[j];
        }
       // System.out.println("Corresponding postfix expression is as follows: ");
//        for(int j=0;j<infix_to_postfix.length;j++)
//        {
//            System.out.print(infix_to_postfix[j]+" ");
//        }
//        System.out.println("Ends here");
//        System.out.println("Corresponding postfix expression is as follows: ");
//        for(int j=0;j<postfix.length;j++)
//        {
//            System.out.print(postfix[j]+" ");
//        }
       // System.out.println("Ends here");
        Num a14;
        a14=evaluatePostfix(infix_to_postfix);
    return a14;
    }

    public static Num fibonacci(int n) {
        Num a = new Num(0);
        Num b = new Num(1);
        for(int i=0; i<n; i++) {
            Num c = Num.add(a,b);
            a = b;
            b = c;
        }
        return a;
        }

        public static Num logFibonacci(int n) {
        int m = 1;
        Num a = new Num(1);
        Num b = a;
        Num c = a;
        Num d = new Num(0);
        Num e = null;
        while(m <= n) {
            e = quad(a,a,b,c);
            Num f = quad(a,b,b,d);
            Num g = quad(a,c,c,d);
            Num h = quad(b,c,d,d);
            a = e;
            b = f;
            c = g;
            d = h;
            m = 2*m;
        }
        return e;
        }

        public static Num quad(Num a, Num b, Num c, Num d) {
        return Num.add(Num.product(a,b), Num.product(c,d));
        }

        public static void print(String s, Num x) {
        System.out.println("Expected output:\n" + s);
        System.out.println("Program output:");
        System.out.println(x);
        }

        public static class Timer {
            long startTime, endTime, elapsedTime, memAvailable, memUsed;

            public Timer() {
                startTime = System.currentTimeMillis();
            }

            public void start() {
                startTime = System.currentTimeMillis();
            }

            public Timer end() {
                endTime = System.currentTimeMillis();
                elapsedTime = endTime-startTime;
                memAvailable = Runtime.getRuntime().totalMemory();
                memUsed = memAvailable - Runtime.getRuntime().freeMemory();
                return this;
            }

            public String toString() {
                return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
            }
        }
        public static void main(String[] args) {
            Num a12;
            
            int val = 0;
            if(args.length > 0) { val = Integer.parseInt(args[0]); }

            Timer timer = new Timer();
            Scanner in = new Scanner(System.in);
               while (in.hasNext()) {
            int com = in.nextInt();
            switch(com) {
            case 1:
                Num x = Num.evaluatePostfix(new String[] { "98765432109876543210987654321",  "5432109876543210987654321", "345678901234567890123456789012", "*", "+", "246801357924680135792468013579", "*", "12345678910111213141516171819202122", "191817161514131211109876543210", "13579", "24680", "*", "-", "*", "+", "7896543", "*", "157984320", "+" });
                print("3659535532566681673026857047264590495633096120170316011130546064934049533282760410899967541", x);
                a12=  x.convertBase(10);
                print_result(a12.arr);
                System.out.println();
                print("1912991252611125159670459142926165956543555293", Num.squareRoot(x)); // 
                System.out.println();
                System.out.println("3659535532566681673026857047264590495633096120170316011130546064934049533282760410899967541 in base 1000:");
                x.convertBase(1000).printList();
                System.out.println();
                System.out.println();
                System.out.println("3659535532566681673026857047264590495633096120170316011130546064934049533282760410899967541 in base 87654321:");
                x.convertBase(87654321).printList();
                System.out.println();
                break;
            case 2:
                print("3659535532566681673026857047264590495633096120170316011130546064934049533282760410899967541", Num.evaluateInfix(new String[] { "(", "(", "98765432109876543210987654321", "+", "5432109876543210987654321", "*", "345678901234567890123456789012", ")", "*", "246801357924680135792468013579", "+", "12345678910111213141516171819202122", "*", "(", "191817161514131211109876543210", "-", "13579", "*", "24680", ")", ")", "*", "7896543", "+", "157984320" })); // 3659535532566681673026857047264590495633096120170316011130546064934049533282760410899967541
                break;
            case 3:
                print("354224848179261915075", fibonacci(100));  // 354224848179261915075
                break;
            case 4:
                print("",fibonacci(100000)); 
                break;
        	case 5:
        	    print("372764047...70016204514", logFibonacci(524287)); 
        	    break;
            case 6:
                a12 = Num.power(new Num(3), 99999);
                print_result(a12.arr);
                break;
            case 7: 
                a12=new Num("31");
                a12=a12.convertBase(16);
                a12=a12.convertBase(10);
                System.out.println("Result is ");
                print_result(a12.arr);
                break;
            default:
                print("206", Num.evaluatePostfix(new String[] { "13", "12", "*", "48", "3", "/", "-", "66", "+" })); // 206
                break;
            }
            System.out.println(timer.end());
//            System.out.println("Problem - 1");
//            Num x = Num.evaluateInfix(new String[] { "(", "(", "98765432109876543210987654321", "+", "5432109876543210987654321", "*", "345678901234567890123456789012", ")", "*", "246801357924680135792468013579", "+", "12345678910111213141516171819202122", "*", "(", "191817161514131211109876543210", "-", "13579", "*", "24680", ")", ")", "*", "7896543", "+", "157984320" });
//            
//           // Num x = Num.evaluatePostfix(new String[] { "98765432109876543210987654321",  "5432109876543210987654321", "345678901234567890123456789012", "*", "+", "246801357924680135792468013579", "*", "12345678910111213141516171819202122", "191817161514131211109876543210", "13579", "24680", "*", "-", "*", "+", "7896543", "*", "157984320", "+" });
//            //System.out.println("Result is ");
//           // print_result(x.arr);
//            print("3659535532566681673026857047264590495633096120170316011130546064934049533282760410899967541", x);
//            System.out.println();
//            System.out.println();
//            System.out.println();
//           // print("1912991252611125159670459142926165956543555293", Num.squareRoot(x));
////            a12 = new Num("10965").convertBase(100);
////           // a12.base = 100;
////            //print_result(a12.arr);
////            print_result(a12.arr);
////            System.out.println("__+++++++++++");
////            Num b = new Num("10965");
////            b.base = 100;
////            b.printList();
//        //   System.out.println("done");
//          
//            
////            System.out.println();
////            System.out.println();
////            System.out.println();
////            System.out.println("Problem - 2");
////            //print("3659535532566681673026857047264590495633096120170316011130546064934049533282760410899967541", Num.evaluateInfix(new String[] { "(", "(", "98765432109876543210987654321", "+", "5432109876543210987654321", "*", "345678901234567890123456789012", ")", "*", "246801357924680135792468013579", "+", "12345678910111213141516171819202122", "*", "(", "191817161514131211109876543210", "-", "13579", "*", "24680", ")", ")", "*", "7896543", "+", "157984320" }));
////            System.out.println("Problem - 3");
////            //print("354224848179261915075", fibonacci(100)); 
////            System.out.println("Problem - 4");
//            
////            
////            try { 
////                System.setOut(new PrintStream(new File("output-file.txt")));
////            } catch (Exception e) {
////                 e.printStackTrace();
////            }
////              a12 = Num.power(new Num(3),99999);
//             //a12 = Num.product(new Num(31),new Num(123456789));
////              System.out.println("**************");
////              print_result(a12.arr);
////              System.out.println("after result");
////            a12 = logFibonacci(100000);
////            print_result(a12.arr);
//        
//             
////            System.out.println();
////            System.out.println();
////            System.out.println();
////            System.out.println();
////            System.out.println();
////            System.out.println("started");
////            print_result(a12.arr);
//          // System.exit(0);
               }
            
        }
}
