//
//  GitHubUserListViewModel.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import Foundation
import SwiftUI
import Combine

import shared
import KMPNativeCoroutinesCombine

@MainActor
class GitHubUserListViewModel: ObservableObject {
    @Published public var items: [GitHubUser] = []
    @Published public var isLoading = false
    @Published public var isFetchingFinished = false
    @Published public var errorHappened = false
    let service = GitHubUserService()
    var cancellables = Set<AnyCancellable>()

    init() {
        createPublisher(for: service.isFetchingFinishedNative())
            .sink { _ in } receiveValue: { [unowned self] value in
                self.isFetchingFinished = value.boolValue
            }
            .store(in: &cancellables)

        service.getUsers().subscribe { data in
            guard let listItems = data?.compactMap({ $0 as? GitHubUser }) else {
                return
            }
            self.items = listItems
            self.isLoading = false
        }
    }

    public func searchUser(userName: String) async {
        self.isLoading = true
        self.errorHappened = false
        do {
            try await service.searchUser(userName: userName)
        } catch {
            self.errorHappened = true
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
            self.errorHappened = true
        }

        service.getUsers().subscribe { data in
            guard let listItems = data?.compactMap({ $0 as? GitHubUser }) else {
                return
            }
            self.items = listItems
        }
    }
}
