# SwipeMenuItemProject
## 继承自ViewGroup的快速实现列表Item的侧滑菜单
### 本库暂时只实现了Item的侧滑,具体的关于在列表显示中菜单显示与关闭的问题暂没有封装到库内,但是演示的代码里面提供了较为完整的实现方式,请参照

#### Step 1. Add the JitPack repository to your build file
#### Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
#### Step 2. Add the dependency
```
	dependencies {
	        compile 'com.github.Brioal:SwipeMenuItemProject:1.0'
	}
```
