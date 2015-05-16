package net.kreid.decisiontree;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by Kevin on 16/05/2015.
 */
public class Sounds
{
    // TODO - make singleton

    private SoundPool _pool;

    private int sCorrect;
    private int sWrong;
    private int sBtnClick;

    Dictionary<SoundType, Integer> _sounds;

    public Sounds(Context cxt)
    {
        _pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        sCorrect = _pool.load(cxt, R.raw.correct, 1);
        sWrong = _pool.load(cxt, R.raw.wrong, 1);
        sBtnClick = _pool.load(cxt, R.raw.buttonclick1, 1);

        _sounds = new Hashtable<SoundType, Integer>();
        _sounds.put(SoundType.CORRECT, sCorrect);
        _sounds.put(SoundType.WRONG, sWrong);
        _sounds.put(SoundType.BUTTON_CLICK, sBtnClick);
    }

    public void PlaySound(SoundType type)
    {
        int sound = _sounds.get(type);
        _pool.play(sound, 1.0f, 1.0f, 0, 0, 1.5f);
    }

    public enum SoundType
    {
        BUTTON_CLICK, CORRECT, WRONG
    }
}
