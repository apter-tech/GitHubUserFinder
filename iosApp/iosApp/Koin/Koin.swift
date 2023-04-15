//
//  Koin.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 10. 24..
//  Copyright © 2022. orgName. All rights reserved.
//

import Foundation
import shared

public typealias KoinApplication = Koin_coreKoinApplication
public typealias Koin = Koin_coreKoin

extension KoinApplication {
    static let shared = {
        let baseUrl = (Bundle.main.object(forInfoDictionaryKey: "BASE_URL") as? String) ?? ""
        return KoinIOSKt.doInitKoinIos(baseUrl: baseUrl, doOnStartup: {})
    }()

    @discardableResult
    static func start() -> KoinApplication {
        shared
    }

    static func getLogger<T>(class: T) -> KermitLogger {
        shared.koin.loggerWithTag(tag: String(describing: T.self))
    }
}

extension KoinApplication {
    static func inject<T: AnyObject>() -> T {
        shared.inject()
    }

    func inject<T: AnyObject>() -> T {
        guard let kotlinClass = koin.get(objCClass: T.self) as? T else {
            fatalError("\(T.self) is not registered with KoinApplication")
        }

        return kotlinClass
    }
}
