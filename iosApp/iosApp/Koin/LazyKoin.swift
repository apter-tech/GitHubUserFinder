//
//  LazyKoin.swift
//  iosApp
//
//  Created by Imre Kaszab on 2023. 04. 14..
//  Copyright © 2023. orgName. All rights reserved.
//

import shared

@propertyWrapper
struct LazyKoin<T: AnyObject> {
    lazy var wrappedValue: T = { KoinApplication.shared.inject() }()

    init() { }
}
