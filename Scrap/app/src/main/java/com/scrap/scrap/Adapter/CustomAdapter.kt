package com.scrap.scrap

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.scrap.scrap.Retrofit.Data.DeleteScrapResult
import com.scrap.scrap.Retrofit.Data.ListScrapObject
import com.scrap.scrap.Retrofit.RetrofitService
import com.scrap.scrap.TEST.TestData
import com.scrap.scrap.UI.MainActivity
import com.scrap.scrap.databinding.BottomSheetBinding
import com.scrap.scrap.databinding.ItemGridBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CustomAdapter(data: ArrayList<ListScrapObject>?) : RecyclerView.Adapter<ViewHolder>() {

    val data = data
    var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*
        val testData = data.get(position)

        val spinner = holder.binding.spinner2

        holder.setData(testData)

        holder.binding.root.setOnClickListener {
            Log.i("MYTAG", "Let's go")
            Log.i("MYTAG", testData.text2)
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(testData.text2))
            startActivity(context!!,intent,null)
        }

        spinner.adapter = ArrayAdapter.createFromResource(context!!, R.array.itemList, android.R.layout.simple_list_item_1)
//        spinner.adapter = ArrayAdapter.createFromResource(context!!, R.array.itemList, R.layout.spinner_text)
//        spinner.adapter = ArrayAdapter<String>(context!!, R.layout.spinner_text, sData)

//        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                when (p2) {
//                    1 -> {
//                        data.removeAt(position)
//                        Log.i("MYTAG", "delete")
//                        notifyDataSetChanged()
//                    }
//                }
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//
//        }

         */

        // 이미지 바인딩
//        Log.i("MYTAG", "imgUrl: "+data?.get(position)?.imgUrl)
        Glide.with(holder.context)
            .load(data?.get(position)?.imgUrl)
            .into(holder.binding.itemImg)
        // 타이틀 바인딩
        holder.binding.itemTitle.text = data?.get(position)?.title
        // 도메인 바인딩
        holder.binding.itemDomain.text = data?.get(position)?.domain

        holder.binding.root.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(data?.get(position)?.link))
            startActivity(holder.context,intent,null)
        }

//        holder.binding.root.setOnLongClickListener {
//            makeBottomDialog()
//            return@setOnLongClickListener(true)
//        }

        holder.binding.imageView11.setOnClickListener {
//            makeBottomDialog()

// BottomSheetDialog 객체 생성. param : Context
            val dialog = BottomSheetDialog(context!!)

// 해당 BottomSheetDialog에 layout 설정. 직접 레이아웃 파일을 넣어도 되고(R.layout.~), layout 객체를 넣어도 됨.
            dialog.setContentView(R.layout.bottom_sheet)

// BottomSheetdialog 종료 시(뒤로가기 버튼) 발생하는 이벤트 리스너 삽입
            dialog.setOnCancelListener { }

// BottomSheetdialog 외부 화면(회색) 터치 시 종료 여부 boolean(false : ㄴㄴ, true : 종료하자!)
            dialog.setCanceledOnTouchOutside(true)

// create()와 show()를 통해 출력!
            dialog.create()
            dialog.show()

            dialog.findViewById<Button>(R.id.btn_del)?.setOnClickListener {
                Log.i("MYTAG", data?.get(position)?.linkId.toString())

                RetrofitService.create().
                deleteScrap(MyApplication.prefs.getId("id",0),data?.get(position)?.linkId).
                enqueue(object: Callback<DeleteScrapResult>{
                    override fun onResponse(
                        call: Call<DeleteScrapResult>,
                        response: Response<DeleteScrapResult>
                    ) {
                        Log.i("MYTAG", response.body()?.message!!)

                        dialog.dismiss()

                        val intent = Intent(holder.context,MainActivity::class.java)
                        intent.putExtra("del","del")
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(holder.context,intent,null)
                    }

                    override fun onFailure(call: Call<DeleteScrapResult>, t: Throwable) {
                        Log.i("MYTAG", t.message.toString())
                        Log.i("MYTAG", "FAIL")
                    }
                })



            }
        }




    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    fun makeBottomDialog() {
    }
}

class ViewHolder(val binding: ItemGridBinding, var context: Context) : RecyclerView.ViewHolder(binding.root) {
}