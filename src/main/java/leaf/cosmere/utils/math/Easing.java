/*
 * File created ~ 11 - 6 - 2022 ~ Leaf
 */

package leaf.cosmere.utils.math;

public class Easing
{

	public static float easeOutQuad(float x)
	{
		return 1 - (1 - x) * (1 - x);
	}
}
