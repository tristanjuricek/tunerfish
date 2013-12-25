package com.tristanjuricek.tunerfish;

import javax.swing.*;

public class Tunerfish {

    public static void main(String[] argv) {

        final MixerListAdaptor listAdaptor = new MixerListAdaptor();
        final AudioDispatcherManager audioDispatcherManager = new AudioDispatcherManager();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TunerfishForm form = new TunerfishForm(listAdaptor, audioDispatcherManager);
                form.pack();
                form.setVisible(true);
            }
        });
    }
}
