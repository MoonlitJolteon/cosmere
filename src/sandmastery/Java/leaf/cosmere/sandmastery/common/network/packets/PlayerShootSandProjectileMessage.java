/*
 * File updated ~ 8 - 10 - 2022 ~ Leaf
 */

package leaf.cosmere.sandmastery.common.network.packets;

import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.common.network.ICosmerePacket;
import leaf.cosmere.sandmastery.common.registries.SandmasteryItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class PlayerShootSandProjectileMessage implements ICosmerePacket
{
	public PlayerShootSandProjectileMessage()
	{
		//empty
	}

	@Override
	public void encode(FriendlyByteBuf buf)
	{
		//empty
	}

	public static PlayerShootSandProjectileMessage decode(FriendlyByteBuf buf)
	{
		return new PlayerShootSandProjectileMessage();
	}

	@Override
	public void handle(NetworkEvent.Context context)
	{
		ServerPlayer player = context.getSender();
		MinecraftServer server = player.getServer();
		server.submitAsync(() -> SpiritwebCapability.get(player).ifPresent((cap) ->
		{
			for (int i = 0; i < player.getInventory().getContainerSize(); i++)
			{
				ItemStack pouch = player.getInventory().getItem(i);
				if (!pouch.isEmpty() && pouch.is(SandmasteryItems.SAND_POUCH_ITEM.get()))
				{
					SandmasteryItems.SAND_POUCH_ITEM.get().shoot(pouch, player);

					return;
				}
			}
		}));
	}

}
