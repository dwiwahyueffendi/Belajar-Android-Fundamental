package com.example.preloaddata.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.preloaddata.R
import com.example.preloaddata.databinding.ItemMahasiswaRowBinding
import com.example.preloaddata.model.MahasiswaModel
import java.util.*
import kotlin.collections.ArrayList

class MahasiswaAdapter: RecyclerView.Adapter<MahasiswaAdapter.MahasiswaHolder>() {
    private val listMahasiswa = ArrayList<MahasiswaModel>()

    fun setData(listMahasiswa: ArrayList<MahasiswaModel>) {
        if (listMahasiswa.size > 0) {
            this.listMahasiswa.clear()
        }

        this.listMahasiswa.addAll(listMahasiswa)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mahasiswa_row, parent, false)
        return MahasiswaHolder(view)
    }

    override fun onBindViewHolder(holder: MahasiswaHolder, position: Int) {
        holder.bind(listMahasiswa[position])
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = listMahasiswa.size

    inner class MahasiswaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMahasiswaRowBinding.bind(itemView)

        fun bind(mahasiswa: MahasiswaModel) {
            binding.txtNim.text = mahasiswa.nim
            binding.txtName.text = mahasiswa.name

            val random = Random()
            val color =
                Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
            binding.imageView.setColorFilter(color)
        }
    }
}