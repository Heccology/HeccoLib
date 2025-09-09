package net.hecco.nexuslib.lib.compat;

import net.hecco.nexuslib.NexusLib;
import net.hecco.nexuslib.platform.NLServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CompatManager {

    protected final String modId;
    protected final List<ModIntegration> INTEGRATIONS = new ArrayList<>();
    public final Map<Supplier<?>, ModIntegration> CONTENT = new HashMap<>();

    protected CompatManager(String modId) {
        this.modId = modId;
    }

    public void addIntegration(ModIntegration module) {
        INTEGRATIONS.add(module);
    }

    public List<ModIntegration> getIntegrations() {
        return this.INTEGRATIONS;
    }

    public void registerCompatContent() {
        for (ModIntegration integration : INTEGRATIONS) {
            integration.registerContent();
            if (integration.shouldCreateDatapack() && integration.modIds().stream().allMatch(NLServices.PLATFORM::isModLoaded)) {
                if (integration.modIds().isEmpty()) {
                    NexusLib.LOGGER.error("Cannot create datapack with no mod ids for integration " + integration);
                    continue;
                }
                StringBuilder id = new StringBuilder();
                for (String modId : integration.modIds()) {
                    id.append(modId).append("_");
                }
                id.append("dat");
                NLServices.REGISTRY.registerBuiltInDatapack(modId, id.toString(), integration.getDatapackName() != null ? integration.getDatapackName() : id.toString(), true, true);
            }
        }
    }
}
