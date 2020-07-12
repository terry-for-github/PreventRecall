/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Events;


import gameevent.GameEvent;
import net.mamoe.mirai.contact.Group;
import preventrecall.PreventRecall;

/**
 *
 * @author Administrator
 */
public class OpenPrevent extends GameEvent {

    public OpenPrevent() {
        super("开启防撤回");
    }
    
    public void Do(Group group)
    {
        PreventRecall.canrecall=true;
        group.sendMessage("已开启防撤回");
    }

}
