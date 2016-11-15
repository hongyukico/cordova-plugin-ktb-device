//
//  Attribute.m
//  HelloCordova
//
//  Created by zyj7815 on 16/9/23.
//
//

#import "CDVAttributePlugin.h"
#import "CDVZYJReachability.h"



static CordovaSingleton *singletonClass = nil;

@implementation CordovaSingleton

+ (CordovaSingleton *)singletonClass {
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        singletonClass = [[CordovaSingleton alloc]init];
        singletonClass.classNameArr = [NSMutableArray arrayWithObject:@{@"class":@"root",@"url":@"root",@"native":@"root"}];
        singletonClass.pageParam = @"";
    });
    return singletonClass;
}


+ (id)allocWithZone:(struct _NSZone *)zone {
    
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        singletonClass = [super allocWithZone:zone];
    });
    return singletonClass;
}

@end



@implementation CDVAttributePlugin

#pragma mark - 获取属性
- (void)getDeviceInfo:(CDVInvokedUrlCommand *)command {
    
    [self.commandDelegate runInBackground:^{
        
        NSString *version = [NSString stringWithFormat:@"%@",
                             [[NSBundle mainBundle] objectForInfoDictionaryKey:
                              (NSString*)kCFBundleVersionKey]];
        CGFloat width = [UIScreen mainScreen].bounds.size.width;
        CGFloat height = [UIScreen mainScreen].bounds.size.height;
        CordovaSingleton *singleton = [CordovaSingleton singletonClass];
        
        NSDictionary *dict = @{
                               @"appVersion":version,//应用版本号
                               @"systemType":@"iOS",//系统类型
                               @"connectionType":[self networkType],//网络类型
                               @"screenWidth":@(width),
                               @"screenHeight":@(height),
                               @"winWidth":@(width),
                               @"winHeight":@(height),
                               @"pageParam":singleton.pageParam
                               };
        
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:dict options:NSJSONWritingPrettyPrinted error:nil];
        NSString *jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        
        CDVPluginResult *pluginResult = nil;
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                                         messageAsString:jsonString];
        [self.commandDelegate sendPluginResult:pluginResult
                                    callbackId:command.callbackId];
    }];
}



/**
 * 判断网络连接状态
 */
- (NSString *)networkType {
    
    CDVZYJReachability *reach = [CDVZYJReachability reachabilityWithHostName:@"wap.baidu.com"];
    if ([reach currentReachabilityStatus] == CDVZYJNotReachable) {
        return @"none";
    }
    else if ([reach currentReachabilityStatus] == CDVZYJReachableViaWiFi) {
        NSLog(@"wifi");
        return @"wifi";
    }
    else if ([reach currentReachabilityStatus] == CDVZYJReachableViaWWAN2G) {
        NSLog(@"2G");
        return @"2G";
    }
    else if ([reach currentReachabilityStatus] == CDVZYJReachableViaWWAN3G) {
        NSLog(@"3g");
        return @"3G";
    }
    else if ([reach currentReachabilityStatus] == CDVZYJReachableViaWWAN4G) {
        NSLog(@"4g");
        return @"4G";
    }
    return @"unknown";
}

@end
