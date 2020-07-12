/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Events;


import gameevent.GameEvent;
import preventrecall.PreventRecall;
import net.mamoe.mirai.contact.Group;

/**
 *
 * @author Administrator
 */
public class ClosePrevent extends GameEvent{
    
    public ClosePrevent() {
        super("关闭防撤回");
    }
    
    public void Do(Group group)
    {
        PreventRecall.canrecall=false;
        group.sendMessage("已关闭防撤回");
    }
    
}
