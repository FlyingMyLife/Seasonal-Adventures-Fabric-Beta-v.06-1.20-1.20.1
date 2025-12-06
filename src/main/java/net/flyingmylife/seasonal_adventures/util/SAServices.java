package net.flyingmylife.seasonal_adventures.util;

import net.flyingmylife.seasonal_adventures.network.service.ServerDataQueryService;

public class SAServices {
    public static void registerServices() {
        ServerDataQueryService.registerDataTypes();
    }
}
