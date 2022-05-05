package io.github.pepe20129.custom_fishing_lines.mixin;

import io.github.pepe20129.custom_fishing_lines.Config;
import io.github.pepe20129.custom_fishing_lines.ConfigHelper;
import io.github.pepe20129.custom_fishing_lines.CustomFishingLines;
import io.github.pepe20129.custom_fishing_lines.Pattern;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(FishingBobberEntityRenderer.class)
public abstract class FishingBobberEntityRendererMixin {
	@Inject(method = "render(Lnet/minecraft/entity/projectile/FishingBobberEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/FishingBobberEntityRenderer;renderFishingLine(FFFLnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/util/math/MatrixStack$Entry;FF)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
	private void modifyRender(FishingBobberEntity fishingBobberEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci, PlayerEntity playerEntity, MatrixStack.Entry entry, Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, int j, ItemStack itemStack, float h, float k, float l, double d, double e, double m, double n, double o, double p, double q, float r, double s, double t, double u, float v, float w, float x, VertexConsumer vertexConsumer2, MatrixStack.Entry entry2, int y, int z) {
		if (z == 0) {
			Config config = ConfigHelper.getConfig();
			int segmentCount;
			if (config.enabled && CustomFishingLines.PATTERNS.containsKey(config.pattern)) {
				segmentCount = CustomFishingLines.PATTERNS.get(config.pattern).segments.size();
				if (segmentCount <= 5) {
					segmentCount *= 3;
				} else if (segmentCount <= 10) {
					segmentCount *= 2;
				}
			} else {
				segmentCount = 16;
			}
			for (int a = 0; a <= segmentCount; a += 1) {
				customRenderFishingLine(v, w, x, vertexConsumer2, entry2, a / (float)segmentCount, (a + 1) / (float)segmentCount);
			}
		}
	}

	@Redirect(method = "render(Lnet/minecraft/entity/projectile/FishingBobberEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/FishingBobberEntityRenderer;renderFishingLine(FFFLnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/util/math/MatrixStack$Entry;FF)V"))
	private void redirectRender(float x, float y, float z, VertexConsumer entry2, MatrixStack.Entry matrices, float segmentStart, float segmentEnd) {

	}

	@Unique
	private float customFishingLines$xPercentage;
	@Unique
	private float customFishingLines$g;
	@Unique
	private float customFishingLines$zPercentage;
	@Unique
	private float customFishingLines$xPercentageLeft;
	@Unique
	private float customFishingLines$j;
	@Unique
	private float customFishingLines$zPercentageLeft;
	@Unique
	private VertexConsumer customFishingLines$buffer;
	@Unique
	private MatrixStack.Entry customFishingLines$matrices;

	@Unique
	private void customRenderFishingLine(float x, float y, float z, VertexConsumer buffer, MatrixStack.Entry matrices, float segmentStart, float segmentEnd) {
		customFishingLines$buffer = buffer;
		customFishingLines$matrices = matrices;
		customFishingLines$xPercentage = x * segmentStart;
		customFishingLines$g = y * (segmentStart * segmentStart + segmentStart) * 0.5F + 0.25F;
		customFishingLines$zPercentage = z * segmentStart;
		customFishingLines$xPercentageLeft = x * segmentEnd - customFishingLines$xPercentage;
		customFishingLines$j = y * (segmentEnd * segmentEnd + segmentEnd) * 0.5F + 0.25F - customFishingLines$g;
		customFishingLines$zPercentageLeft = z * segmentEnd - customFishingLines$zPercentage;
		float pythagorasPercentageLeft = MathHelper.sqrt(customFishingLines$xPercentageLeft * customFishingLines$xPercentageLeft + customFishingLines$j * customFishingLines$j + customFishingLines$zPercentageLeft * customFishingLines$zPercentageLeft);
		customFishingLines$xPercentageLeft /= pythagorasPercentageLeft;
		customFishingLines$j /= pythagorasPercentageLeft;
		customFishingLines$zPercentageLeft /= pythagorasPercentageLeft;

		Config config = ConfigHelper.getConfig();

		if (config.enabled && CustomFishingLines.PATTERNS.containsKey(config.pattern)) {
			List<Pattern.Segment> segments = CustomFishingLines.PATTERNS.get(config.pattern).segments;
			for (int i = 0; i < segments.size(); i += 1) {
				if ((i == 0 || segmentStart <= 1 - Math.pow(1 - segments.get(i - 1).percentage, config.percentageBias)) && segmentStart > 1 - Math.pow(1 - segments.get(i).percentage, config.percentageBias)) {
					String hexColor = segments.get(i).color;
					int red = Integer.parseInt(hexColor.substring(0, 2), 16);
					int green = Integer.parseInt(hexColor.substring(2, 4), 16);
					int blue = Integer.parseInt(hexColor.substring(4, 6), 16);
					addMatrices(red, green, blue);
				}
			}
		} else {
			addMatrices(0, 0, 0);
		}
	}

	@Unique
	private void addMatrices(int red, int green, int blue) {
		customFishingLines$buffer.vertex(customFishingLines$matrices.getPositionMatrix(), customFishingLines$xPercentage, customFishingLines$g, customFishingLines$zPercentage).color(red, green, blue, 255).normal(customFishingLines$matrices.getNormalMatrix(), customFishingLines$xPercentageLeft, customFishingLines$j, customFishingLines$zPercentageLeft).next();
	}
}