package com.android.aop.part2.toycrimenotification.data.repo

import com.android.aop.part2.toycrimenotification.data.source.local.CriminalLocalDataSource
import com.android.aop.part2.toycrimenotification.data.source.remote.CriminalRemoteDataSource

interface CriminalRepository : CriminalRemoteDataSource, CriminalLocalDataSource