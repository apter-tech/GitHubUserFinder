//
//  GitHubUserListViewModelImpl.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine
import KMPNativeCoroutinesCombine
import KMPNativeCoroutinesAsync

@MainActor
class GitHubUserListViewModelImpl: ObservableObject {
    var viewModel = GitHubUserListViewModel()

    @Published public var items: [GitHubUser] = []
    @Published public var isLoading = false
    @Published public var isFetchingFinished = false
    @Published public var error: String = ""
    var cancellables = Set<AnyCancellable>()

    init() {
        createPublisher(for: viewModel.isFetchingFinishedNative)
            .receive(on: DispatchQueue.main)
            .sink { _ in } receiveValue: { [weak self] value in
                self?.isFetchingFinished = value.boolValue
            }
            .store(in: &cancellables)

        createPublisher(for: viewModel.usersNative)
            .receive(on: DispatchQueue.main)
            .sink { _ in } receiveValue: { [weak self] value in
                self?.items = value
            }
            .store(in: &cancellables)

        createPublisher(for: viewModel.isLoadingNative)
            .receive(on: DispatchQueue.main)
            .sink { _ in } receiveValue: { [weak self] value in
                self?.isLoading = value.boolValue
            }
            .store(in: &cancellables)

        createPublisher(for: viewModel.errorNative)
            .receive(on: DispatchQueue.main)
            .sink { _ in } receiveValue: { [weak self] value in
                self?.error = value
            }
            .store(in: &cancellables)
    }
}
