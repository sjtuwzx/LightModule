# LightModule

##简介
一款轻量级的模块化框架，可将复杂页面切分成多个独立模块。框架自动控制模块显隐及创建、刷新时机。支持全局刷新及指定部分模块刷新，支持多级嵌套、模块懒加载，提供模块必要的生命周期

##推荐使用方式

1. 逻若不考虑模块跨页面复用，所有模块使用一个统一的数据模型（DataSouce），构建以数据为中心的页面架构，模块状态完全依赖数据，而非事件
2. 模块内部事件触发UI刷新，不应直接刷新UI控件，而是刷新DataSouce中相应的数据，然后调用requestRefresh方法刷新受影响模块
3. 各个模块应保持相互独立，不应存在依赖关系
	