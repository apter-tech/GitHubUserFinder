//
//  GitHubUserListViewModel.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import Foundation
import SwiftUI

import shared

class GitHubUserListViewModel: ObservableObject {
    @Published public var items: [GitHubUser] = []
    @Published public var isLoading = false
    let service = GitHubUserService()

    @MainActor
    public func searchUser(userName: String) async {
        self.isLoading = true

        do {
            try await service.searchUser(userName: userName)
        } catch {
            self.isLoading = false
        }

        service.getUsers().subscribe { data in
            guard let listItems = data?.compactMap({ $0 as? GitHubUser }) else {
                return
            }
            self.items = listItems
            self.isLoading = false
        }
    }
}
