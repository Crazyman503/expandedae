package lu.kolja.expandedae.client;

import lu.kolja.expandedae.Expandedae;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Credits to <a href="https://github.com/GrowthcraftCE/Growthcraft-1.20/blob/release/src/main/java/growthcraft/bamboo/ResourcePackHandler.java">Growthcraft</a>
 * for allowing me to copy their code.
 */
@Mod.EventBusSubscriber(modid = Expandedae.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ResourcePackHandler {
    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) return;
        var modFileInfo = ModList.get().getModFileById(Expandedae.MODID);
        if (modFileInfo == null) return;
        var modFile = modFileInfo.getFile();
        event.addRepositorySource(consumer -> {
            var pack = Pack.readMetaAndCreate(
                    Expandedae.makeId("exp_blackout").toString(), Component.literal("Builtin blackout textures for Expanded AE"),
                    false, id -> getInstance(id, modFile, "exp_blackout"),
                    PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN
            );
            if (pack == null) return;
            consumer.accept(pack);
        });
    }

    private static @NotNull PathPackResources getInstance(String id, IModFile modFile, String path) {
        if (instances.containsKey(id)) {
            return instances.get(id);
        }
        else {
            PathPackResources result = new ExpPackResources(id, modFile, true, "packs/" + path);
            instances.put(id, result);
            return result;
        }
    }
    private static final Map<String, PathPackResources> instances = new HashMap<>();

    //////////////////////////////

    private static class ExpPackResources extends PathPackResources {
        /**
         * Constructs a java.nio.Path-based resource pack.
         *  @param packId    the identifier of the pack.
         *                  This identifier should be unique within the pack finder, preferably the name of the file or folder containing the resources.
         * @param modFile    needed to provide a good Path object; not all are the same.
         * @param isBuiltin  whether this pack resources should be considered builtin
         * @param sourcePath the root path of the pack. This needs to point to the folder that contains "assets" and/or "data", not the asset folder itself!
         */
        public ExpPackResources(String packId, IModFile modFile, boolean isBuiltin, String sourcePath) {
            super(packId, isBuiltin, modFile.findResource(sourcePath));
            this.modFile = modFile;
            this.sourcePath = sourcePath + "/";
        }
        private final IModFile modFile;
        private final String sourcePath;

        @Override
        protected Path resolve(String... paths) {
            return this.modFile.getSecureJar().getPath(this.sourcePath + String.join("/", paths));
        }
    }
}
