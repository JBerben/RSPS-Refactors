  private void method91(Stream stream, int i)
  {
    while (stream.bitPosition + 10 < i * 8)
    {
      int j = stream.readBits(11);
      if (j == 2047) {
        break;
      }
      if (this.playerArray[j] == null)
      {
        this.playerArray[j] = new Player(j);
        if (this.aStreamArray895s[j] != null) {
          this.playerArray[j].updatePlayer(this.aStreamArray895s[j]);
        }
      }
      this.playerIndex[(this.playerCount++)] = j;
      Player player = this.playerArray[j];
      player.anInt1537 = loopCycle;
      int k = stream.readBits(1);
      if (k == 1) {
        this.anIntArray894[(this.anInt893++)] = j;
      }
      int l = stream.readBits(1);
      int i1 = stream.readBits(5);
      if (i1 > 15) {
        i1 -= 32;
      }
      int j1 = stream.readBits(5);
      if (j1 > 15) {
        j1 -= 32;
      }
      int direction = stream.readBits(3);
      player.turnDirection = (1024 + direction * 256);
      player.setNewPosition(myPlayer.smallX[0] + j1, myPlayer.smallY[0] + i1, l == 1);
    }
    stream.finishBitAccess();
  }