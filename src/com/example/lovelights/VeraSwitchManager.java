package com.example.lovelights;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.os.AsyncTask;

public class VeraSwitchManager extends AsyncTask<URL, Integer, Long> implements List<VeraSwitch> {
	
	List<VeraSwitch> list;
	
	public VeraSwitchManager() {
		
		this.list = new ArrayList<VeraSwitch>();
	}

	@Override
	public boolean add(VeraSwitch object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void add(int location, VeraSwitch object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addAll(Collection<? extends VeraSwitch> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends VeraSwitch> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VeraSwitch get(int location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<VeraSwitch> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lastIndexOf(Object object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<VeraSwitch> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<VeraSwitch> listIterator(int location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VeraSwitch remove(int location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VeraSwitch set(int location, VeraSwitch object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<VeraSwitch> subList(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] array) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Long doInBackground(URL... params) {
		// TODO Auto-generated method stub
		return null;
	} 
}
