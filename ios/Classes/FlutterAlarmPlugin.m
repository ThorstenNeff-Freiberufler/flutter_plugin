#import "FlutterAlarmPlugin.h"

@implementation FlutterAlarmPlugin

UIWindow* alertWindow;

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"flutter_alarm"
            binaryMessenger:[registrar messenger]];
  FlutterAlarmPlugin* instance = [[FlutterAlarmPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void) showRating {

    /*
   if (@available(iOS 10.3, *)) {
       [SKStoreReviewController requestReview];
   } else {
   */
       UIAlertController * alert = [UIAlertController
                                    alertControllerWithTitle:nil
                                    message:@"Wenn Dir die App gefallen hat, würden wir uns über eine Bewertung im AppStore freuen."
                                    preferredStyle:UIAlertControllerStyleAlert];

       UIAlertAction* yesButton = [UIAlertAction
                                   actionWithTitle:@"Bewerten"
                                   style:UIAlertActionStyleDefault
                                   handler:^(UIAlertAction * action) {
                                       //Handle your yes please button action here
                                       [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"itms-apps://itunes.apple.com/WebObjects/MZStore.woa/wa/viewContentsUserReviews?id=481007071&onlyLatestVersion=true&pageNumber=0&sortOrdering=1&type=Purple+Software"]];
                                   }];

       UIAlertAction* noButton = [UIAlertAction
                                  actionWithTitle:@"Nein, jetzt nicht"
                                  style:UIAlertActionStyleDefault
                                  handler:^(UIAlertAction * action) {
                                      //Handle no, thanks button
                                  }];

       [alert addAction:yesButton];
       [alert addAction:noButton];

       alertWindow = [[UIWindow alloc] initWithFrame: [UIScreen mainScreen].bounds];
       alertWindow.rootViewController = [UIViewController new];
       alertWindow.windowLevel = UIWindowLevelAlert + 1;
       [alertWindow makeKeyAndVisible];
       [alertWindow.rootViewController presentViewController: alert animated: YES completion: nil];

   // }
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  } else if ([@"showRating" isEqualToString:call.method]) {
    [self showRating];
  } else {
    result(FlutterMethodNotImplemented);
  }
}

@end
