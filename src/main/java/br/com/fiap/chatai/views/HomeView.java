package br.com.fiap.chatai.views;

import br.com.fiap.chatai.ChatService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Route("")
public class HomeView extends VerticalLayout {

    private final ChatService chatService;

    public HomeView(ChatService chatService) {

        MessageList talkPanel = new MessageList();
        talkPanel.setHeight("100%");
        talkPanel.setWidthFull();
        talkPanel.getStyle().set("background-color", "gray");

        TextField textField = new TextField();

        Consumer<String> sendMessageAction = message ->{
            var userMessage = new MessageListItem(message, Instant.now(), "Aluno");
            var chatMessage = new MessageListItem(chatService.sendChatMessage(message), Instant.now(), "Professor");
            System.out.println(userMessage);
            if (!Objects.equals(message, ""))
                talkPanel.setItems(List.of(userMessage, chatMessage));
                textField.clear();
        };

        textField.setPlaceholder("Digite sua pergunta");
        textField.setClearButtonVisible(true);
        textField.setWidth("80%");
        textField.setPrefixComponent(VaadinIcon.CHAT.create());
        //textField.addValueChangeListener(event -> send(textField.getValue(), talkPanel, textField));
        HorizontalLayout messagePanel = new HorizontalLayout();
        messagePanel.setWidth("100%");

        Scroller scroller = new Scroller(talkPanel);
        scroller.setHeight("100%");
        scroller.setWidthFull();

        Button sendButton = new Button(new Icon(VaadinIcon.PAPERPLANE));
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.setAriaLabel("enviar mensagem");
        sendButton.addClickListener(event -> send(textField.getValue(), talkPanel, textField));

        messagePanel.add(textField, sendButton);

        setHeightFull();

        add(new H1("Chat com Professor de Ciências"));
        add(messagePanel);
        add(scroller);
        this.chatService = chatService;
    }

    private void send(String message, MessageList talkPanel, TextField textField){
        var userMessage = new MessageListItem(message, Instant.now(), "Aluno");
        var chatMessage = new MessageListItem(chatService.sendChatMessage(message), Instant.now(), "Professor");

        userMessage.setUserColorIndex(1);
        chatMessage.setUserColorIndex(2);
        var currentMessages = new ArrayList<>(talkPanel.getItems());
        if (!Objects.equals(message, ""))
            currentMessages.add(userMessage);
            currentMessages.add(chatMessage);
            talkPanel.setItems(currentMessages);
            textField.clear();
    }

}
