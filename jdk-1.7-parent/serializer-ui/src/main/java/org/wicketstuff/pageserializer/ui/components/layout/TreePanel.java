package org.wicketstuff.pageserializer.ui.components.layout;

import org.wicketstuff.pageserializer.ui.colors.Colors;
import org.wicketstuff.pageserializer.ui.colors.Colors.HSB;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TreePanel extends TilePane {

	public TreePanel() {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.RED);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        borderGlow.setHeight(12.0);
        borderGlow.setWidth(12.0);
        
        FlowPane root = new FlowPane();
        root.setHgap(3.0);
        root.setVgap(2.0);
        root.getChildren().add(btn);
        Label label = new Label();
        label.setText("foo");
        label.setFont(new Font(22));
        label.setEffect(borderGlow);
		root.getChildren().add(label);
		root.getChildren().add(new RandomColorPane());
		root.getChildren().add(new RandomColorPane());
//        primaryStage.setScene(new Scene(root, 300, 250));
//        primaryStage.show();
        
		getChildren().add(root);
		
//        walkTree(root, new Consumer<Node>() {
//        	@Override
//        	public void accept(Node element) {
//        		System.out.println("Node: "+element.getId()+":"+element.getClass());
//        	}
//		});
//        
//        walkUp(label, new Consumer<Node>() {
//        	@Override
//        	public void accept(Node element) {
//        		System.out.println("Up: "+element.getId()+":"+element.getClass());
//        	}
//		});
	}
	
	
	
	static class RandomColorPane extends Pane {
		
		public RandomColorPane() {
			String hexColor=Colors.asRGBHex(Colors.colorFromHashcode(this.hashCode(), Integer.MAX_VALUE, Colors.hsb(0f, 1.0f, 1.0f)));
			setHeight(100);
			setWidth(50);
			setStyle("-fx-background-color:#"+hexColor);
			getChildren().add(new Button("foo"));
			setMinSize(100, 100);
			setPrefSize(100, 100);
			setMaxSize(100, 100);
			setPadding(new Insets(2));
		}
	}
}
