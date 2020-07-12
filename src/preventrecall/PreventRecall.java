/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preventrecall;

import Events.ClosePrevent;
import Events.OpenPrevent;
import static gamerobot.GameRobot.pool;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.events.MessageRecallEvent.GroupRecall;
import net.mamoe.mirai.japt.Events;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;

/**
 *
 * @author Administrator
 */
public class PreventRecall {

    /**
     * @param args the command line arguments
     */
    public static Map<Long, Map<Integer, MessageChain>> messages = new ConcurrentHashMap<>();
    public static boolean canrecall = true;

    public static void main(String[] args) {
        ClosePrevent close = new ClosePrevent();
        OpenPrevent open = new OpenPrevent();
        if (canrecall) {

            //群组撤回消息复述
            Events.subscribeAlways(GroupRecall.class, (GroupRecall event) -> {
                pool.submit(new Thread() {
                    @Override
                    public void run() {
                        if (messages.containsKey(event.getGroup().getId()) && messages.get(event.getGroup().getId()).containsKey(event.getMessageId()) && ((Member) event.getOperator()).getPermission().equals(MemberPermission.MEMBER)) {
                            event.getGroup().sendMessage(messages.get(event.getGroup().getId()).get(event.getMessageId()).plus("\n" + event.getOperator().getNick() + "(" + event.getOperator().getId() + ")"));
                            messages.get(event.getGroup().getId()).remove(event.getMessageId());
                        }
                    }
                });
            }
            );
            //群组撤回消息缓存
            Events.subscribeAlways(MessageEvent.class, (MessageEvent event) -> {
                pool.submit(new Thread() {
                    @Override
                    public void run() {
                        if (event.getSubject() instanceof Group) {
                            if (!messages.containsKey(((Group) event.getSubject()).getId())) {
                                messages.put(((Group) event.getSubject()).getId(), new HashMap<>());
                            }
                            messages.get(((Group) event.getSubject()).getId()).put(event.getSource().getId(), event.getMessage());
                        }
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (messages.containsKey(((Group) event.getSubject()).getId()) && messages.get(((Group) event.getSubject()).getId()).containsKey(event.getSource().getId())) {
                                    messages.get(((Group) event.getSubject()).getId()).remove(event.getSource().getId());
                                }

                            }
                        }, 120000);
                    }
                });
            }
            );
        }

        // TODO code application logic here
    }

    public static void Save() {

    }

}
