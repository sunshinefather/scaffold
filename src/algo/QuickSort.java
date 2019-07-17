package algo;

/**
 * 快速排序算法
 * @ClassName:  QuickSort   
 * @Description:
 * @author: sunshine  
 * @date:   2017年12月11日 下午3:30:24
 */
public class QuickSort {
	
	/**
	 * 对整个数组进行快速排序
	 * @Title:  QuickSort   
	 * @Description:
	 * @param:  @param arr  
	 * @throws
	 */
	private QuickSort(){  
       throw new RuntimeException("不能被实例化");
    }
	
	/**
	 * 递归地对左右子序列进行快速排序
	 * @Title: quickSort
	 * @Description:
	 * @param: @param arr
	 * @param: @param begin
	 * @param: @param end      
	 * @return: void
	 * @author: sunshine  
	 * @throws
	 */
	public static void quickSort(int[] arr){  
		quickSort(arr,0,arr.length-1);
    }
	
	private static void quickSort(int[] arr, int begin, int end){  
        if(begin<end){  
            int pivot = partition(arr, begin, end); // 轴值：取第一个数为轴值  
            quickSort(arr, begin, pivot-1); // 对左子序列排序  
            quickSort(arr, pivot+1, end);// 对右子序列排序  
        }  
    }
	
	/**
	 * 划分
	 * @Title: partition
	 * @Description:
	 * @param: @param arr
	 * @param: @param begin
	 * @param: @param end
	 * @param: @return      
	 * @return: int
	 * @author: sunshine  
	 * @throws
	 */
	private static int partition(int[] arr, int begin, int end){  
        while(begin<end){  
            // 右侧扫描  
            while(begin<end && arr[begin]<=arr[end])  
                end--;  
            // 将较小值交换到前面  
            if(begin<end){  
                swap(arr, begin, end);  
                begin++;  
            }  
              
            // 左侧扫描  
            while(begin<end && arr[begin]<=arr[end])  
                begin++;  
            // 将较大值交换到后面  
            if(begin<end){  
                swap(arr, begin, end);  
                end--;  
            }  
        }         
        // begin为轴值记录的最终位置  
        return begin;  
    }
	
	/**
	 * 交换数组的两个数
	 * @Title: swap
	 * @Description:
	 * @param: @param arr
	 * @param: @param i
	 * @param: @param j      
	 * @return: void
	 * @author: sunshine  
	 * @throws
	 */
	private static void swap(int[] arr, int i, int j) {  
        arr[i] = arr[i]^arr[j];          
        arr[j] = arr[i]^arr[j];          
        arr[i] = arr[i]^arr[j];          
    }
}
