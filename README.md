# AdhellReborn no-root system adblock (no VPN)
Open-source ads and trackers blocker for Samsung devices. This is a personal fork of the original Adhell that is no longer being worked on. I have no intentions of publishing this but feel free to compile this for yourself.
Do not, I repeat, do NOT publish this app. I will not be held responsible for what consequences may come from your lack of responsibility. Just because it is open source does not mean you are free to publish and claim credit.

## FAQ
### How AdhellReborn works?
With Knox Standard SDK: https://seap.samsung.com/sdk/knox-standard-android

### Only Samsung?
Yes


### Do I have to install the MyKnox app too?
No

### Does it block on everything or just Samsung's browser?
Blocks ads system wide.

### What exactly AdhellReborn blocks?
AdhellReborn takes urls to block from two sources (at least for now):
 - https://adaway.org/hosts.txt
 - http://pgl.yoyo.org/adservers/serverlist.php?hostformat=hosts&showintro=0&mimetype=plaintext

Then sorts them by popularity, checks if urls are reachable. And finally generates this list: https://github.com/adhell/adhell.github.io/blob/master/urls-to-block.json

For more information about generating urls-to-block list look at this repo: https://github.com/adhell/adprovider


### Which is better, AdhellReborn or Disconnect Pro?
AdhellReborn is free and open source.

### If I have disconnect pro and advantages to running this as well?
Disconnect Pro and AdhellReborn are using the same underlying Knox Standard SDK. If Disconnect Pro is running Samsung Firewall AdhellReborn doesn't have rights to change Firewall settings and vice versa. So they can't work together at the same time.

### Any noticeable battery drain using this?
No

### Need to be rooted?
No

### What about YouTube native app?
You may see some ads.

### I tried this but some in-app ads were still appearing
Try rebooting. The ads might have been cached.

### Is it okay to use AdhellReborn with Adguard?
Adguard (without root) will set up a local vpn to route adds to nowhere basically. I think with root it uses a proxy. Either way, it's different than how AdhellReborn does it, so they should work side by side just fine.
