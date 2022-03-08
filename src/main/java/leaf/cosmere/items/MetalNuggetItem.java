/*
 * File created ~ 24 - 4 - 2021 ~ Leaf
 */

package leaf.cosmere.items;

import leaf.cosmere.cap.entity.SpiritwebCapability;
import leaf.cosmere.constants.Manifestations;
import leaf.cosmere.constants.Metals;
import leaf.cosmere.utils.helpers.TextHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MetalNuggetItem extends MetalItem
{
    public MetalNuggetItem(Metals.MetalType metalType)
    {
        super(metalType);
    }


    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);

        //todo convert to shavings

        consumeNugget(playerIn, getMetalType(), itemstack);

        return ActionResult.consume(itemstack);
    }

    public static void consumeNugget(LivingEntity livingEntity, Metals.MetalType metalType, ItemStack itemstack)
    {
        if (metalType == null)
        {
            return;
        }

        SpiritwebCapability.get(livingEntity).ifPresent(iSpiritweb ->
        {
            SpiritwebCapability spiritweb = (SpiritwebCapability) iSpiritweb;

            if (metalType == Metals.MetalType.LERASIUM || metalType == Metals.MetalType.LERASATIUM)
            {
                for (int i = 0; i < 16; i++)
                {
                    switch (metalType)
                    {
                        case LERASIUM:
                            //give allomancy
                            spiritweb.giveManifestation(Manifestations.ManifestationTypes.ALLOMANCY, i);

                            //https://www.theoryland.com/intvmain.php?i=977#43
                            if (livingEntity instanceof LlamaEntity && !livingEntity.hasCustomName())
                            {
                                //todo translations
                                livingEntity.setCustomName(TextHelper.createTranslatedText("Mistborn Llama"));
                            }

                            break;
                        case LERASATIUM:
                            //give feruchemy
                            spiritweb.giveManifestation(Manifestations.ManifestationTypes.FERUCHEMY, i);
                            break;
                    }
                }
            }
            else
            {
                //add to metal stored
                Integer metalIngested = spiritweb.METALS_INGESTED.get(metalType);
                spiritweb.METALS_INGESTED.put(metalType,metalIngested + 9); // todo decide what value should be used for ingestion
            }

            if (livingEntity instanceof PlayerEntity && !((PlayerEntity) livingEntity).isCreative())
            {
                itemstack.shrink(1);
            }
        });
    }
}
