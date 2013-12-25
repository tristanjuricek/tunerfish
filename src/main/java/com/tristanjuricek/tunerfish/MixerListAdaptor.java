package com.tristanjuricek.tunerfish;

import javax.sound.midi.MidiDevice;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 * Adapts the array of mixer info available to lists that can back information.
 */
public class MixerListAdaptor {

    private Mixer.Info[] infos;
    private Mixer.Info selected;
    private List<SelectionChangedListener> listeners;

    public MixerListAdaptor(Mixer.Info[] infos) {
        this.infos = infos;
        this.selected = this.infos[0];
        listeners = new ArrayList<SelectionChangedListener>();
    }

    public MixerListAdaptor() {
        this(AudioSystem.getMixerInfo());
    }

    public ComboBoxModel<String> getMixerDescriptions() {
        return new MixerDescriptions();
    }

    public static class MixerInfoEvent extends EventObject {

        private Mixer.Info info;

        public MixerInfoEvent(Object source, Mixer.Info info) {
            super(source);
        }

        public Mixer.Info getInfo() {
            return info;
        }
    }

    public static interface SelectionChangedListener {

        void newMixer(MixerInfoEvent event);
    }

    public void addSelectionChangedListener(SelectionChangedListener listener) {
        listeners.add(listener);
    }

    public void removeSelectionChangedListener(SelectionChangedListener listener) {
        listeners.remove(listener);
    }

    public Mixer.Info getSelected() {
        return selected;
    }

    private void triggerSelectionChanged() {
        MixerInfoEvent event = new MixerInfoEvent(this, selected);
        for (SelectionChangedListener listener : listeners) {
            listener.newMixer(event);
        }
    }

    public class MixerDescriptions implements ComboBoxModel<String> {

        @Override
        public void setSelectedItem(Object anItem) {
            String itemStr = (String)anItem;
            for (Mixer.Info ii : infos) {
                if (itemStr.equals(ii.getName())) {
                    selected = ii;
                    triggerSelectionChanged();
                }
            }
        }

        @Override
        public Object getSelectedItem() {
            if (selected != null) {
                return selected.getName();
            }
            return null;
        }

        @Override
        public int getSize() {
            return infos.length;
        }

        @Override
        public String getElementAt(int index) {
            return infos[index].getName();
        }

        @Override
        public void addListDataListener(ListDataListener l) {

        }

        @Override
        public void removeListDataListener(ListDataListener l) {

        }
    }
}
