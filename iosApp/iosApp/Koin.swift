//
//  Koin.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 10. 24..
//  Copyright © 2022. orgName. All rights reserved.
//

import Foundation
import shared

func startKoin(baseUrl: String) {

    let doOnStartup = {}

    let koinApplication = KoinIOSKt.doInitKoinIos(
        baseUrl: baseUrl,
        doOnStartup: doOnStartup
    )
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin?
public var koin: Koin_coreKoin {
    return _koin!
}
