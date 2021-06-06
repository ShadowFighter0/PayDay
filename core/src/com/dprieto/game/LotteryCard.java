package com.dprieto.game;

public class LotteryCard extends Card{

  public LotteryCard(Level level) {
    super("Lottery!", "", level);
    visualCard = AssetManager.getInstance().getTexture("BargainCard");

    dimension.x = visualCard.getRegionWidth() * 2.5f;
    dimension.y = visualCard.getRegionHeight() * 2.5f;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }
}
