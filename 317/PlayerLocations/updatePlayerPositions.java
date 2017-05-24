public void updatePlayerPositions(int packetSize, JByteBuffer byteBuffer) {
		while (byteBuffer.bitPosition + 10 < packetSize * 8) {
			int indice = byteBuffer.getBits(11);
			if (indice == 2047)
				break;
			if (localPlayers[indice] == null) {
				localPlayers[indice] = new Player();
				if (byteBuffers[indice] != null)
					localPlayers[indice].updatePlayer(byteBuffers[indice]);
			}
			playerIndices[totalLocalPlayers++] = indice;
			Player player = localPlayers[indice];
			player.currentCycle = Game.loopCycle;
			int xChange = byteBuffer.getBits(5);
			if (xChange > 15)
				xChange -= 32;
			int l = byteBuffer.getBits(1);
			if (l == 1)
				localInteractableIndices[currentInteractableIndice++] = indice;
			int runFlag = byteBuffer.getBits(1);
			int yChange = byteBuffer.getBits(5);
			if (yChange > 15)
				yChange -= 32;
			player.setPosition(((Actor) Game.localPlayer).pathX[0] + xChange,
					((Actor) Game.localPlayer).pathY[0] + yChange, runFlag == 1);
		}
		return;
	}