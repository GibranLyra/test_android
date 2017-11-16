package gibran.com.br.zaptest.base;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import gibran.com.br.zaptest.R;

/**
 * Created by gibranlyra on 08/11/17.
 */

public abstract class BaseFragment<T extends BaseContractPresenter> extends Fragment implements BaseContractView<T> {
    protected abstract void reloadFragment();

    public void showImovelError() {
        Snackbar.make(getActivity().findViewById(R.id.rootLayout), R.string.generic_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_try_again, v -> reloadFragment())
                .show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
