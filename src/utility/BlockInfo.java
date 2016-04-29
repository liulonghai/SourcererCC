package utility;

import models.Bag;
import models.QueryBlock;

public class BlockInfo {
	private int prefixSize;
	private int computedThreshold;
	
	public BlockInfo(float threshold, QueryBlock queryBlock){
		this.computedThreshold =  this.getMinimumSimilarityThreshold(queryBlock, threshold);
        this.prefixSize =  (queryBlock.getSize() + 1) - this.computedThreshold;
	}
	
	public BlockInfo(float threshold, Bag bag){
		this.computedThreshold =  this.getMinimumSimilarityThreshold(bag, threshold);
        this.prefixSize =  (bag.getSize() + 1) - this.computedThreshold;
	}
	
	public int getPrefixSize() {
		return prefixSize;
	}

	public int getComputedThreshold() {
		return computedThreshold;
	}

	private int getMinimumSimilarityThreshold(QueryBlock queryBlock,float threshold) {
        return (int) Math.ceil((threshold * queryBlock.getSize())/ (Util.MUL_FACTOR*10));
    }
	private int getMinimumSimilarityThreshold(Bag bag,float threshold) {
        return (int) Math.ceil((threshold * bag.getSize())/ (Util.MUL_FACTOR*10));
    }
	
	public static int getMinimumSimilarityThreshold(int size,float threshold) {
        return (int) Math.ceil((threshold * size)/ (Util.MUL_FACTOR*10));
    }
	public static int getMaximumSimilarityThreshold(int size,float threshold) {
        return (int) Math.floor((size*Util.MUL_FACTOR*10)/threshold);
    }
	
	public static float getMinimumSimilarityThreshold(float size,float threshold) {
        return (threshold * size)/ (Util.MUL_FACTOR*10);
    }
    public static float getMaximumSimilarityThreshold(float size,float threshold) {
        return (size*Util.MUL_FACTOR*10)/threshold;
    }
    public static long getMinimumSimilarityThreshold(long size,float threshold) {
        return (long) Math.ceil((threshold * size)/ (Util.MUL_FACTOR*10));
    }
    public static long getMaximumSimilarityThreshold(long size,float threshold) {
        return (long) Math.floor((size*Util.MUL_FACTOR*10)/threshold);
    }
	
	public static int getPrefixSize(int size, int computedThreshold){
		return (size + 1) - computedThreshold;
	}
	
}
