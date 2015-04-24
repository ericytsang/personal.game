package framework.net;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import net.Packet;
import framework.Controller;
import game.PairType;

public class ServerController extends framework.net.Entity implements Controller
{
    private Queue<Packet> events;

    private framework.Entity controllee;

    public ServerController()
    {
        super(PairType.SVRCTRL_NETCTRL);
        this.events = new LinkedBlockingQueue<>();
    }

    public void setControllee(framework.Entity controllee)
    {
        this.controllee = controllee;
    }

    public void addEvent(Packet packet)
    {
        events.add(packet);
        update(packet);
    }

    @Override
    public final Packet[] getEvents()
    {
        controllee.serverUpdate();
        Packet[] eventsArr = new Packet[events.size()];
        events.toArray(eventsArr);
        events.clear();
        return eventsArr;
    }

    @Override
    public final void onUpdate(Packet packet)
    {
        events.add(packet);
    }

    @Override
    public Packet getRegisterPacket()
    {
        return new Packet()
            .pushData(controllee.toBytes())
            .pushData(controllee.getClass().getSimpleName().getBytes());
    }

    @Override
    public void onUnregister(Packet packet)
    {
        // TODO Auto-generated method stub
        
    }
}
