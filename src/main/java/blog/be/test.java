package blog.be;

import java.util.HashMap;

import static java.util.Collections.swap;

public class test {
    public static int[] twoSum(int[] nums, int target) {
        HashMap map = new HashMap();
        for(int i=0;i<nums.length;i++){
            int check = target - nums[i];
            if(map.containsKey(check)){
                return new int[] {(int) map.get(check), i};
            }
            map.put(nums[i],i);
        }
        return null;
    }
    public static void main(String[] args) {
       int[] a = {5,3,4,1,7,2};
        System.out.println();
    }
}
