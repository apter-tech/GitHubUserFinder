//
//  StringResource+Extensions.swift
//  iosApp
//
//  Created by Imre Kaszab on 2023. 06. 19..
//  Copyright © 2023. orgName. All rights reserved.
//

import shared

extension StringResource {
    func localize() -> String {
        self.desc().localized()
    }
    func localize(input: String) -> String {
        self.format(args_: [input]).localized()
    }
}
