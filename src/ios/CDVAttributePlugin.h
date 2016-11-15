//
//  Attribute.h
//  HelloCordova
//
//  Created by zyj7815 on 16/9/23.
//
//

#import "Cordova/CDV.h"


@interface CordovaSingleton : NSObject

+ (CordovaSingleton *)singletonClass;
/**
 html自定义栈数组
 */
@property (nonatomic, strong)NSMutableArray *classNameArr;
/**
 页面参数
 */
@property (nonatomic, copy)NSString *pageParam;

@end

@interface CDVAttributePlugin : CDVPlugin

/**
 *  获取属性
 */
- (void)getDeviceInfo:(CDVInvokedUrlCommand *)command;


@end



