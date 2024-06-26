package com.example.ilovetruyen.ui.dashboard;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ilovetruyen.R;
import com.example.ilovetruyen.adapter.FavoriteComicAdapter;
import com.example.ilovetruyen.api.ComicAPI;
import com.example.ilovetruyen.database.DBHelper;
import com.example.ilovetruyen.databinding.FragmentFavoriteComicsBinding;
import com.example.ilovetruyen.retrofit.RetrofitService;

import java.util.ArrayList;

public class FavoriteComicsFragment extends Fragment {
    private FragmentFavoriteComicsBinding binding;
    private FavoriteComicAdapter favoriteComicAdapter;
    ArrayList<String> conmicsId, comicsName, comicsThumb, comicsCurr;
    private DBHelper dbHelper;
    View rootView;
    ComicAPI comicAPI;
    RetrofitService retrofitService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

         FavoriteComicsViewModel favoriteComicsViewModel =
                new ViewModelProvider(this).get(FavoriteComicsViewModel.class);
        rootView =inflater.inflate(R.layout.fragment_favorite_comics,container,false);

//        binding = FragmentFavoriteComicsBinding.inflate(R.layout.fragment_favorite_comics, container, false);
//        View root = binding.getRoot();

        conmicsId = new ArrayList<>();
        comicsName = new ArrayList<>();
        comicsThumb = new ArrayList<>();
        comicsCurr = new ArrayList<>();

        renderFavoriteComics(rootView);


        return rootView;
    }

    private void renderFavoriteComics(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.favoriteComicsRecycleView);
        favoriteComicAdapter = new FavoriteComicAdapter(getContext());
        getfavoriteComicsData();
        favoriteComicAdapter.setData(conmicsId,comicsName,comicsThumb,comicsCurr); //

        recyclerView.setAdapter(favoriteComicAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));


    }
    //NOTE: đổ dữ liệu comics yêu thích ở đây
    public void getfavoriteComicsData() {
        dbHelper= new DBHelper(requireContext());
        Cursor cursor = dbHelper.getAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(requireContext(),"Danh sách trống",Toast.LENGTH_SHORT).show();
        }else {
            while(cursor.moveToNext()){
                conmicsId.add(cursor.getString(0).trim());
                comicsName.add(cursor.getString(1).trim());
                comicsThumb.add(cursor.getString(2).trim());
                comicsCurr.add(cursor.getString(3).trim());
            }
        }

    }
    private void showError() {
        Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}