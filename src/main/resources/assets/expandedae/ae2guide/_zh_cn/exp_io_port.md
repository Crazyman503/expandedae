---
navigation:
  parent: expandedae-index.md
  title: 拓展IO端口
  icon: exp_io_port
  position: 1
categories:
  - expandedae
item_ids:
- expandedae:exp_io_port
---

<GameScene zoom="4" background="transparent">
  <ImportStructure src="structures/exp_io_port.snbt" />
  <IsometricCamera yaw="195" pitch="30" />
</GameScene>

更强大的IO端口，能够以极快的速度传输大量物品。
与<ItemLink id="ae2:condenser" />配合能够制作大量奇点。
具体数值请查看下表！

| 升级数量 | 传输速度 | 传输速度（易读形式） |
|--------------------|----------------|-----------------------------|
| 0                  | 4,194,303      | MAX_INT / 512               |
| 1                  | 8,388,606      | MAX_INT / 256               |
| 2                  | 33,554,424     | MAX_INT / 64                |
| 3                  | 134,217,696    | MAX_INT / 16                |
| 4                  | 536,870,784    | MAX_INT / 4                 |
| 5                  | 2,147,483,136  | MAX_INT                     |

_注：在Java中，Integer.MAX_VALUE（整型上限）——此处写为为MAX_INT——的值为2,147,483,647_