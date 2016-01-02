package org.to2mbn.basiscommands.teleportrequest;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import com.google.common.collect.Sets;
import org.to2mbn.basiscommands.BasisCommands;

import java.util.Collections;
import java.util.Set;

public class TeleportRequestsHandler extends PluginTask<BasisCommands> {
    protected final Set<TeleportRequest> requests;
    private final int waitTick;
    private int currentTick = 0;

    public TeleportRequestsHandler(BasisCommands owner, int maxWaitSeconds) {
        super(owner);
        waitTick = 20 * maxWaitSeconds;
        requests = Collections.synchronizedSet(Sets.newHashSet());
    }

    public TeleportRequest pickRequest(Player targetPlayer) {
        for (TeleportRequest request : requests) {
            if (request.getTargetPlayer().getName().equals(targetPlayer.getName())) {
                cancelRequest(request);
                return request;
            }
        }
        return null;
    }

    public void addRequest(TeleportRequest request) {
        if (requests.contains(request)) {
            requests.remove(request);
        }
        requests.add(request);
    }

    public void cancelRequest(TeleportRequest request) {
        requests.remove(request);
    }

    @Override
    public void onRun(int tick) {
        currentTick = tick;

        for (TeleportRequest request : requests) {
            if (request != null) {
                int requestTime = request.getTime();
                if (currentTick - requestTime >= waitTick) {
                    cancelRequest(request);
                }
            }
        }
    }
}
