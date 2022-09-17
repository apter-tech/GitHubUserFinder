//
//  FavouriteUsersViewModelImpl.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 09. 17..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine
import KMPNativeCoroutinesCombine
import KMPNativeCoroutinesAsync

@MainActor
class FavouriteUsersViewModelImpl: ObservableObject {
    var viewModel = FavouriteUsersViewModel()

    @Published public var error: String = ""
    @Published public var itemList: [GitHubUser] = []
    var cancellables = Set<AnyCancellable>()

    init() {
    createPublisher(for: viewModel.usersNative)
        .receive(on: DispatchQueue.main)
        .sink { _ in } receiveValue: { [weak self] value in
            self?.itemList = value
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
