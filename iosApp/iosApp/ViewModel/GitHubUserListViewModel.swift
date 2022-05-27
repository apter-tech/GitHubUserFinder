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

@MainActor
class GitHubUserListViewModel: ObservableObject {
    @Published public var items: [GitHubUser] = []
    @Published public var isLoading = false
    @Published public var isFetchingFinished = false
    let service = GitHubUserService()

    init() {
        service.isFetchingFinished().subscribe { data in
            self.isFetchingFinished = data?.boolValue ?? false
        }
    }

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

    public func refreshUserDetails(with userName: String) async {
        self.isLoading = true

        do {
            try await service.refreshUserDetails(userName: userName)
        } catch {
            self.isLoading = false
        }
    }

    public func fetchNextPage() async {
        do {
            try await service.fetchNextPage()
        } catch {
            // TODO handle error
        }

        service.getUsers().subscribe { data in
            guard let listItems = data?.compactMap({ $0 as? GitHubUser }) else {
                return
            }
            self.items = listItems
        }
    }
}
