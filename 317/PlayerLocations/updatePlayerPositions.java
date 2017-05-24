/* updatePlayerPositions() takes 2 arguments. packetSize and byteBuffer.
 *
 * byteBuffer: This is how Runescape transfers its data. In their native client,
 * the JByteBuffer object is referred to as a "Stream", which streams packets
 * back and forth from the players client. These packets are contained within
 * byte buffers.
 *
 * SIDENODE: This method is called within another method in the Client class
 * called "updatePlayers()"
 */
public void updatePlayerPositions(int packetSize, JByteBuffer byteBuffer) {
		while (byteBuffer.bitPosition + 10 < packetSize * 8) {

			// These bits being read from the buffer contain the index # of the player
			// in the Players array
			int indice = byteBuffer.getBits(11);

			// Breaks if player array is full
			if (indice == 2047)
				break;

			// Creates new player data if the data for the specified player doesn't
			// exist
			if (localPlayers[indice] == null) {
				localPlayers[indice] = new Player();

				// Updates and reloads everything about the specified player
				if (byteBuffers[indice] != null)
					localPlayers[indice].updatePlayer(byteBuffers[indice]);
			}

			/* The first 5 bits of the byte buffer contain the Δx value
			 * of the player, whilst the next 5 bits contain the Δy value.
			 */
			playerIndices[totalLocalPlayers++] = indice;
			Player player = localPlayers[indice];
			player.currentCycle = Game.loopCycle;

			int xChange = byteBuffer.getBits(5);
			if (xChange > 15) {
				xChange -= 32;
			}

			int l = byteBuffer.getBits(1);
			if (l == 1) {
				localInteractableIndices[currentInteractableIndice++] = indice;
			}

			int runFlag = byteBuffer.getBits(1);
			int yChange = byteBuffer.getBits(5);
			if (yChange > 15) {
				yChange -= 32;
			}

			// Finally updates the position of the player
			player.setPosition(((Actor) Game.localPlayer).pathX[0] + xChange,
					((Actor) Game.localPlayer).pathY[0] + yChange, runFlag == 1);
		}
		return;
	}
