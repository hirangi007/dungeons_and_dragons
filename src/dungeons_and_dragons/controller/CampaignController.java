package dungeons_and_dragons.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.JsonSyntaxException;

import dungeons_and_dragons.helper.FileHelper;
import dungeons_and_dragons.helper.LogHelper;
import dungeons_and_dragons.model.CampaignModel;
import dungeons_and_dragons.model.GameMapModel;
import dungeons_and_dragons.model.ItemModel;
import dungeons_and_dragons.view.CampaignView;

/**
 * @author Tejas Sadrani
 *
 */

public class CampaignController implements ActionListener {

	private CampaignModel campaignModel;
	private CampaignView campaignView;
	private GameMapModel map_model = new GameMapModel();
	private ArrayList<GameMapModel> input_map_list;
	private ArrayList<GameMapModel> output_map_list;

	/**
	 * Constructor of Campaign Controller used to construct campaign models and campaign views
	 */
	public CampaignController() {
		
		//creating campaign model
		this.campaignModel = new CampaignModel();
		
		this.input_map_list = new ArrayList<GameMapModel>();
		this.output_map_list = new ArrayList<GameMapModel>();
		
		try {
			this.input_map_list = FileHelper.getMaps();
		} catch (JsonSyntaxException | IOException e) {
			LogHelper.Log(LogHelper.TYPE_ERROR, e.getMessage());
		}
		
		this.campaignModel.setInput_map_list(input_map_list);

		//creating campaign view
		this.campaignView = new CampaignView(input_map_list);
		
		this.campaignModel.addObserver(campaignView);

		// set listener
		this.campaignView.setActionListener(this);

		// show game view
		this.campaignView.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getSource());
		System.out.println(this.campaignView.campaign_add);
		System.out.println(e.getSource().equals(campaignView.campaign_add));
		
		if(e.getSource().equals(this.campaignView.campaign_add)){
			
			this.map_model = (GameMapModel)this.campaignView.campaign_combobox.getSelectedItem();
			this.output_map_list.add(map_model);
			this.input_map_list.remove(map_model);
			
			if(map_model != null)
			{
				this.campaignModel.setInput_map_list(this.input_map_list);
				this.campaignModel.setOutput_map_list(this.output_map_list);
			}
			else 
			{
				JOptionPane.showOptionDialog(null,
						"There are no more maps",
						"Invalid", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] {},
						null);
				return;
			}
		}else if (e.getSource().equals(this.campaignView.moveMapUP)) {

			int moveMe = this.campaignView.output_map_list.getSelectedIndex();
                //not already at top
                swap(moveMe, moveMe - 1);
		}else if(e.getSource().equals(this.campaignView.moveMapDown)){
			int moveMe = this.campaignView.output_map_list.getSelectedIndex();
                //not already at top
                swap(moveMe, moveMe + 1);
		}else if(e.getSource().equals(this.campaignView.removeMap)){
			int moveMe = this.campaignView.output_map_list.getSelectedIndex();
                //remove a map
               this.output_map_list.remove(moveMe);
               this.input_map_list.add(this.campaignView.output_map_list.getSelectedValue());
               this.campaignModel.setInput_map_list(this.input_map_list);
               this.campaignModel.setOutput_map_list(this.output_map_list);
		}
		else if(e.getSource().equals(this.campaignView.back_button)){
				new ManageCampaignController();
				this.campaignView.dispose();
		}
		else if(e.getSource().equals(this.campaignView.save_button)){
			//first lets validate for a map name
			if(this.campaignView.campaign_name_text.getText()!=null && !this.campaignView.campaign_name_text.getText().equals("")){
				this.campaignModel.setCampaign_name(this.campaignView.campaign_name_text.getText());
				this.campaignModel.save();
				JOptionPane.showMessageDialog(this.campaignView,"Campaign "+this.campaignView.campaign_name_text.getText()+" has been saved succesfully");
				new ManageCampaignController();
				this.campaignView.dispose();
			}
			else{
				JOptionPane.showOptionDialog(null,
						"Please provide a campaign name",
						"Invalid", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] {},
						null);
				return;
			}
			
		}
	}
	
	//Swap two elements in the list.
    private void swap(int a, int b) {
        GameMapModel objectA = this.campaignModel.getOutput_map_list().get(a);
        GameMapModel objectB = this.campaignModel.getOutput_map_list().get(b);
        this.output_map_list.set(b, objectA);
        this.output_map_list.set(a, objectB);
        this.campaignModel.setOutput_map_list(this.output_map_list);
    }

}
