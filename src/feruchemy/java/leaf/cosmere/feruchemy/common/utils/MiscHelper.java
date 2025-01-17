/*
 * File updated ~ 24 - 11 - 2022 ~ Leaf
 */

package leaf.cosmere.feruchemy.common.utils;

import leaf.cosmere.api.CosmereAPI;
import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.Metals;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public class MiscHelper
{

	public static void consumeNugget(LivingEntity livingEntity, Metals.MetalType metalType)
	{
		if (metalType == null || livingEntity.level.isClientSide)
		{
			return;
		}

		SpiritwebCapability.get(livingEntity).ifPresent(iSpiritweb ->
		{
			SpiritwebCapability spiritweb = (SpiritwebCapability) iSpiritweb;

			if (metalType == Metals.MetalType.LERASATIUM)
			{
				for (Manifestation manifestation : CosmereAPI.manifestationRegistry())
				{
					//give allomancy
					if (manifestation.getManifestationType() == Manifestations.ManifestationTypes.FERUCHEMY)
					{
						//todo config feruchemy god metal strength
						final double strength = manifestation.getStrength(iSpiritweb, true);
						spiritweb.giveManifestation(manifestation, strength < 13 ? 13 : (int) (strength + 1));
					}
				}
			}

			if (livingEntity instanceof ServerPlayer serverPlayer)
			{
				spiritweb.syncToClients(serverPlayer);
			}

		});
	}


}
