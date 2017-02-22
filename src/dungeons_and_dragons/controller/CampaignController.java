package dungeons_and_dragons.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dungeons_and_dragons.model.CampaignModel;
import dungeons_and_dragons.model.GameMapModel;
import dungeons_and_dragons.view.CampaignView;
import dungeons_and_dragons.view.GameView;

/**
 * @author Hirangi Naik
 *
 */

public class CampaignController implements ActionListener {

	private CampaignModel campaignModel;
	private CampaignView campaignView;

	/**
	 * constructore of game controller
	 */
	public CampaignController() {

		campaignView = new CampaignView();

		// set listener
		campaignView.setActionListener(this);

		// show game view
		campaignView.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(campaignView.add)){
			GameMapModel name=(GameMapModel) this.campaignView.map_combobox.getSelectedItem();
			this.campaignView.campaign.add(name);
			System.out.println(campaignView.campaign);
		}
	}

}