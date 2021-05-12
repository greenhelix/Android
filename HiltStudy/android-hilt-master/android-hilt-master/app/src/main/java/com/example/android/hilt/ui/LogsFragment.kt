/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.hilt.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.android.hilt.LogApplication
import com.example.android.hilt.R
import com.example.android.hilt.data.Log
import com.example.android.hilt.data.LoggerLocalDataSource
import com.example.android.hilt.util.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment that displays the database logs.
 */
// 여기서 hilt를사용하려면 아래의 @AndroidEntryPoint를 적용해야한다.
// android 클래스 수명주기를 따르는 종속 항목 컨테이너가 생성된다.
// hilt는 LogsFragment의 수명주기에 연결된 종속 항목 컨테이너를 생성하고
// LogsFragment에 인스턴스를 삽입할 수 있는 권한이 생긴다.
@AndroidEntryPoint
class LogsFragment : Fragment() {

    // 권한이 생겼으니, 인스턴스를 생성할 필드를 지정해준다. = 필드 삽입 이라한다.
    // private는 비공개인데, hilt로 삽입되면, 비공개가 안된다.
    @Inject lateinit var logger: LoggerLocalDataSource
    @Inject lateinit var dateFormatter: DateFormatter

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
        }
    }
    // onAttach에서 필드를 채운다.
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        populateFields(context)
//    }

    // Hilt로 필드들이 삽입되었기 때문에 onAttach와 populateFields는 필요없어진다.
//    private fun populateFields(context: Context) {
//        logger = (context.applicationContext as LogApplication).serviceLocator.loggerLocalDataSource
//        dateFormatter =
//            (context.applicationContext as LogApplication).serviceLocator.provideDateFormatter()
//    }

    override fun onResume() {
        super.onResume()

        logger.getAllLogs { logs ->
            recyclerView.adapter =
                LogsViewAdapter(
                    logs,
                    dateFormatter
                )
        }
    }
}

/**
 * RecyclerView adapter for the logs list.
 */
private class LogsViewAdapter(
    private val logsDataSet: List<Log>,
    private val daterFormatter: DateFormatter
) : RecyclerView.Adapter<LogsViewAdapter.LogsViewHolder>() {

    class LogsViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.text_row_item, parent, false) as TextView
        )
    }

    override fun getItemCount(): Int {
        return logsDataSet.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val log = logsDataSet[position]
        holder.textView.text = "${log.msg}\n\t${daterFormatter.formatDate(log.timestamp)}"
    }
}
