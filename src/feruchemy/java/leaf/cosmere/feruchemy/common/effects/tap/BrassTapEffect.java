/*
 * File updated ~ 8 - 10 - 2022 ~ Leaf
 */

package leaf.cosmere.feruchemy.common.effects.tap;

import leaf.cosmere.api.Metals;
import leaf.cosmere.feruchemy.common.Feruchemy;
import leaf.cosmere.feruchemy.common.effects.FeruchemyEffectBase;
import leaf.cosmere.feruchemy.common.registries.FeruchemyEffects;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//https://coppermind.net/wiki/Brass#Feruchemical_Use

@Mod.EventBusSubscriber(modid = Feruchemy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BrassTapEffect extends FeruchemyEffectBase
{
	public BrassTapEffect(Metals.MetalType type, MobEffectCategory effectType)
	{
		super(type, effectType);

		//reduce frost damage? theres no cold damage in base minecraft is there?

		//add fire damage?
	}

	@Override
	public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier)
	{
		if (!isActiveTick(entityLivingBaseIn))
		{
			return;
		}

		if (!entityLivingBaseIn.level.isClientSide && amplifier >= 5 && !entityLivingBaseIn.isInWater())
		{
			//set user on fire
			entityLivingBaseIn.setSecondsOnFire(3);
		}

	}

	@SubscribeEvent
	public static void onLivingDamageEvent(LivingDamageEvent event)
	{
		if (event.getSource().getEntity() instanceof LivingEntity livingEntity)
		{
			MobEffectInstance effectInstance = livingEntity.getEffect(FeruchemyEffects.TAPPING_EFFECTS.get(Metals.MetalType.BRASS).get());

			if (effectInstance != null && effectInstance.getDuration() > 0 && effectInstance.getAmplifier() > 3)
			{
				event.getEntity().setSecondsOnFire(effectInstance.getAmplifier());
			}
		}
	}
}
