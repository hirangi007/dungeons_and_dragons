package dungeons_and_dragons.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import dungeons_and_dragons.helper.FileHelper;
import dungeons_and_dragons.helper.LogHelper;
import dungeons_and_dragons.model.CampaignModel;
import dungeons_and_dragons.model.GameMapModel;
import dungeons_and_dragons.view.CampaignView;

/**
 * @author Hirangi Naik and Tejas Sadrani
 *
 */

public class CampaignController implements ActionListener {

	private CampaignModel campaignModel;
	private CampaignView campaignView;
	private ArrayList<GameMapModel> maps;
	private GameMapModel map_model = new GameMapModel();
	private ArrayList<GameMapModel> map_list;

	/**
	 * Constructor of Campaign Controller used to construct campaign models and campaign views
	 */
	public CampaignController() {
		
		//creating campaign model
		this.campaignModel = new CampaignModel();
		
		this.maps = new ArrayList<GameMapModel>();
		try {
			this.maps = FileHelper.getMaps();
		} catch (JsonSyntaxException | IOException e) {
			LogHelper.Log(LogHelper.TYPE_ERROR, e.getMessage());
		}
		
		//creating campaign view
		this.campaignView = new CampaignView(maps);
		
		this.campaignModel.addObserver(campaignView);

		// set listener
		this.campaignView.setActionListener(this);

		// show game view
		this.campaignView.setVisible(true);
		

		map_list = this.campaignModel.getMap_list();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getSource());
		System.out.println(this.campaignView.campaign_add);
		System.out.println(e.getSource().equals(this.campaignView.campaign_add));
		
		if(e.getSource().equals(this.campaignView.campaign_add)){
			map_model = (GameMapModel)this.campaignView.campaign_combobox.getSelectedItem();
			map_list.add(map_model);
			this.campaignModel.setMap_list(map_list);
			this.campaignView.setActionListener(this);
		}
	}

}
